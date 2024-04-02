package gitbal.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gitbal.backend.domain.GitbalApiDto;
import gitbal.backend.domain.GitbalScore;
import gitbal.backend.entity.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final GitbalScore gitbalScore;
    private final GraphQlService graphQlService;


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
        return gitbalScore.calculate(UserInfoDto.of(
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


}
