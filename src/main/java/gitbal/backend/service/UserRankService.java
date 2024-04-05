package gitbal.backend.service;

import gitbal.backend.entity.User;
import gitbal.backend.entity.dto.UserRankScoreResponseDto;
import io.lettuce.core.ScoredValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRankService {

    private final UserService userService;

    public UserRankScoreResponseDto makeUserRankResponse(String username) {
        User findUser = userService.findByUserName(username);
        return UserRankScoreResponseDto.of(findUser.getScore());
    }

}
