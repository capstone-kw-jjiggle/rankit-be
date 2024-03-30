package gitbal.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gitbal.backend.entity.User;
import gitbal.backend.entity.dto.UserInfoDto;
import gitbal.backend.exception.JoinException;
import gitbal.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GraphQlService graphQlService;

    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
            .orElseThrow(() -> new JoinException("유저가 존재하지 않습니다."));
    }


    public UserInfoDto getUserInformation(String nickname) {
        try {
            ResponseEntity<String> response = graphQlService.requestUserInfo(nickname);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode dataNode = root.get("data").get("user");

            return UserInfoDto.of(
                dataNode.get("contributionsCollection").get("totalCommitContributions").asLong(),
                dataNode.get("pullRequests").get("totalCount").asLong()
            );
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public String findUserImg(String userSettingImage, String profileImg) {
        if (userSettingImage == null) {
            return profileImg;
        }
        return userSettingImage;
    }
}
