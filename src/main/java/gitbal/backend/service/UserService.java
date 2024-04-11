package gitbal.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gitbal.backend.domain.GitbalApiDto;
import gitbal.backend.domain.GitbalScore;
import gitbal.backend.domain.SurroundingRankStatus;
import gitbal.backend.domain.UserRaceStatus;
import gitbal.backend.entity.Region;
import gitbal.backend.entity.School;
import gitbal.backend.entity.User;
import gitbal.backend.entity.dto.UserScoreInfoDto;
import gitbal.backend.exception.UserRankException;
import gitbal.backend.repository.SchoolRepository;
import gitbal.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final GitbalScore gitbalScore;
    private final GraphQLService graphQlService;
    private final UserRepository userRepository;
    private final int USER_AROUND_RANGE = 2;


    public GitbalApiDto callUsersGithubApi(String nickname) {
        try {
            ResponseEntity<String> response = graphQlService.requestUserInfo(nickname);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode dataNode = root.get("data").get("user");

            return GitbalApiDto.of(delegateToGitbalScore(dataNode), checkOneDayCommit(dataNode));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private Boolean checkOneDayCommit(JsonNode dataNode) {
        return dataNode.get("yesterdayCommits").get("totalCommitContributions").asLong() > 0;
    }

    private Long delegateToGitbalScore(JsonNode dataNode) {
        return gitbalScore.calculate(UserScoreInfoDto.of(
            dataNode.get("pullRequests").get("totalCount").asLong(),
            dataNode.get("contributionsCollection").get("totalCommitContributions").asLong(),
            dataNode.get("issues").get("totalCount").asLong(),
            dataNode.get("followers").get("totalCount").asLong(),
            dataNode.get("repositories").get("totalCount").asLong()
        ));
    }

    public String findUserImg(String userSettingImage, String profileImg) {
        if (userSettingImage == null) {
            return profileImg;
        }
        return userSettingImage;
    }


    public User findByUserName(String username) {
        return userRepository.findByNickname(username).orElseThrow(UserRankException::new);
    }

    public UserRaceStatus findUsersScoreRaced(Long score) {
        int forwardCount = userRepository.usersScoreRacedForward(score);
        int backwardCount = userRepository.userScoreRacedBackward(score);
        log.info("forwardCount = {} backwardCount = {}", forwardCount, backwardCount);
        SurroundingRankStatus surroundingRankStatus = SurroundingRankStatus.calculateUserForwardBackward(
            forwardCount, backwardCount, USER_AROUND_RANGE);
        log.info("after forwardCount = {} backwardCount = {}", surroundingRankStatus.forwardCount(), surroundingRankStatus.backwardCount());
        return UserRaceStatus.of(
            userRepository.usersScoreRaced(score, surroundingRankStatus.forwardCount(),
                surroundingRankStatus.backwardCount()));
    }

    public School findSchoolByUserName(String username) {
        User findUser = userRepository.findByNickname(username)
            .orElseThrow(() -> new UserRankException("학교를 찾던 도중 유저를 못 찾았습니다"));
        return findUser.getSchool();
    }

    public Region findRegionByUserName(String username) {
        User findUser = userRepository.findByNickname(username)
            .orElseThrow(() -> new UserRankException("지역을 찾던 도중 유저를 못 찾았습니다."));
        return findUser.getRegion();
    }
}
