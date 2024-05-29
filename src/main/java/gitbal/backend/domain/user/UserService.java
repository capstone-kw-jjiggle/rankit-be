package gitbal.backend.domain.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gitbal.backend.domain.majorlanguage.MajorLanguage;
import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.school.School;
import gitbal.backend.global.exception.NotFoundRegionException;
import gitbal.backend.global.exception.NotFoundSchoolException;
import gitbal.backend.global.exception.NotFoundUserException;
import gitbal.backend.global.util.SurroundingRankStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserScoreCalculator userScoreCalculator;
    private final UserInfoService userInfoService;
    private final UserRepository userRepository;
    private final int USER_AROUND_RANGE = 2;


    public Long calculateUserScore(String nickname) {
        try {
            ResponseEntity<String> response = userInfoService.requestUserInfo(nickname);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode dataNode = root.get("data").get("user");
            return delegateToGitbalScore(dataNode);
        } catch (JsonProcessingException e) {
            throw new NotFoundUserException();
            //throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundUserException();
        }
    }

    public boolean checkUserRecentCommit(String username) {
        try {
            ResponseEntity<String> response = userInfoService.requestUserRecentCommit(username);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode dataNode = root.get("data").get("user");
            return checkOneDayCommit(dataNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // TODO : 추후 exception 처리하기
        }
    }


    private boolean checkOneDayCommit(JsonNode dataNode) {
        return dataNode.get("yesterdayCommits").get("totalCommitContributions").asLong() > 0;
    }

    private Long delegateToGitbalScore(JsonNode dataNode) {
        return userScoreCalculator.calculate(
            UserScoreInfoDto.of(dataNode.get("pullRequests").get("totalCount").asLong(),
                dataNode.get("contributionsCollection").get("totalCommitContributions").asLong(),
                dataNode.get("issues").get("totalCount").asLong(),
                dataNode.get("followers").get("totalCount").asLong(),
                dataNode.get("repositories").get("totalCount").asLong()));
    }

    public String findUserImgByUsername(String username) {
        return userRepository.findProfileImgByNickname(username)
            .orElseThrow(NotFoundUserException::new);
    }


    public User findByUserName(String username) {
        return userRepository.findByNickname(username).orElseThrow(NotFoundUserException::new);
    }

    public UserRaceStatus findUsersScoreRaced(Long score) {
        int forwardCount = userRepository.usersScoreRacedForward(score);
        int backwardCount = userRepository.userScoreRacedBackward(score);
        log.info("forwardCount = {} backwardCount = {}", forwardCount, backwardCount);
        SurroundingRankStatus surroundingRankStatus = SurroundingRankStatus.calculateUserForwardBackward(
            forwardCount, backwardCount, USER_AROUND_RANGE);
        log.info("after forwardCount = {} backwardCount = {}",
            surroundingRankStatus.getForwardCount(), surroundingRankStatus.getBackwardCount());
        return UserRaceStatus.of(
            userRepository.usersScoreRaced(score, surroundingRankStatus.getForwardCount(),
                surroundingRankStatus.getBackwardCount()));
    }

    public School findSchoolByUserName(String username) {
        User findUser = userRepository.findByNickname(username)
            .orElseThrow(NotFoundSchoolException::new);
        return findUser.getSchool();
    }

    public Region findRegionByUserName(String username) {
        User findUser = userRepository.findByNickname(username)
            .orElseThrow(NotFoundRegionException::new);
        return findUser.getRegion();
    }

    public void updateUserSchool(User user, School school) {
        user.setSchool(school);
        userRepository.save(user);
    }

    public void updateUserRegion(User user, Region region) {
        user.setRegion(region);
        userRepository.save(user);
    }

    public void updateUserProfileImg(User user, String imgUrl) {
        user.setProfileImg(imgUrl);
        userRepository.save(user);
    }

    public void deleteUserProfileImg(User user) {
        user.setProfileImg(null);
        userRepository.save(user);
    }

    public List<String> findAllUserNames() {
        return userRepository.findAll().stream().map(u -> u.getNickname()).toList();
    }

    public void updateUserScore(User findUser, Long newScore) {
        findUser.updateScore(newScore);
        updateUserRanking();
    }

    private void updateUserRanking() {
        List<User> users = userRepository.findAll(Sort.by("score").descending());
        int rank = 1;
        for (User user : users) {
            user.setUserRank(rank++);
            userRepository.save(user);
        }
    }


    public List<MajorLanguage> findMajorLanguagesByUsername(String username) {
        User findUser = userRepository.findByNickname(username)
            .orElseThrow(NotFoundUserException::new);
        return findUser.getMajorLanguages();
    }
}
