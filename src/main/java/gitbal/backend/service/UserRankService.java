package gitbal.backend.service;

import gitbal.backend.entity.User;
import gitbal.backend.domain.UserRaceStatus;
import gitbal.backend.entity.dto.UserRankRaceResponseDto;
import gitbal.backend.entity.dto.UserRankScoreResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRankService {

    private final UserService userService;

    @Transactional(readOnly = true)
    public UserRankScoreResponseDto makeUserRankResponse(String username) {
        User findUser = userService.findByUserName(username);
        return UserRankScoreResponseDto.of(findUser.getScore());
    }

    @Transactional(readOnly = true)
    public List<UserRankRaceResponseDto> makeUserRankRaceStatusByUsername(String username) {
        User findUser = userService.findByUserName(username);
        UserRaceStatus userRaceStatus = userService.findUsersScoreRaced(findUser.getScore());
        userRaceStatus.addUser(findUser);
        userRaceStatus.sortAroundUsers();
        return userRaceStatus.toUserRankRaceResponseDto(userRaceStatus.aroundUsers());
    }
}
