package gitbal.backend.service;

import gitbal.backend.domain.SchoolRaceStatus;
import gitbal.backend.domain.UserRaceStatus;
import gitbal.backend.entity.School;
import gitbal.backend.entity.User;
import gitbal.backend.entity.dto.SchoolRankRaceResponseDto;
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
    private final SchoolService schoolService;

    @Transactional(readOnly = true)
    public UserRankScoreResponseDto makeUserRankResponse(String username) {
        User findUser = userService.findByUserName(username);
        return UserRankScoreResponseDto.of(findUser.getScore());
    }

    @Transactional(readOnly = true)
    public List<UserRankRaceResponseDto> makeUserRankRaceStatusByUsername(String username) {
        User findUser = userService.findByUserName(username);
        UserRaceStatus userRaceStatus = userService.findUsersScoreRaced(findUser.getScore());
        userRaceStatus.addEntity(findUser);
        userRaceStatus.sortAroundEntitys();
        return userRaceStatus.toResponseDto(userRaceStatus.aroundUsers());
    }


    @Transactional(readOnly = true)
    public SchoolRankRaceResponseDto makeUserRankSchoolStatusByUsername(String username) {
        School findSchool = userService.findSchoolByUserName(username);
        SchoolRaceStatus schoolRaceStatus = schoolService.findSchoolScoreRaced(
            findSchool.getScore());
        schoolRaceStatus.addEntity(findSchool);
        schoolRaceStatus.sortAroundEntitys();
        return schoolRaceStatus.toResponseDto(schoolRaceStatus.aroundUsers(),
            findSchool.getSchoolName());
    }
}
