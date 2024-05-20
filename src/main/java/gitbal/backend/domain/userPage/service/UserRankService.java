package gitbal.backend.domain.userPage.service;

import gitbal.backend.domain.entity.Region;
import gitbal.backend.domain.entity.School;
import gitbal.backend.domain.userPage.dto.RegionRankRaceResponseDto;
import gitbal.backend.domain.userPage.dto.SchoolRankRaceResponseDto;
import gitbal.backend.domain.entity.User;
import gitbal.backend.domain.service.MajorLanguageService;
import gitbal.backend.domain.service.RegionService;
import gitbal.backend.domain.service.SchoolService;
import gitbal.backend.domain.service.UserService;
import gitbal.backend.domain.userPage.dto.UserRankMajorLanguageResponseDto;
import gitbal.backend.domain.userPage.dto.UserRankRaceResponseDto;
import gitbal.backend.domain.userPage.dto.UserRankScoreResponseDto;
import gitbal.backend.global.util.RegionRaceStatus;
import gitbal.backend.global.util.SchoolRaceStatus;
import gitbal.backend.global.util.UserRaceStatus;

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
    private final RegionService regionService;
    private final MajorLanguageService majorLanguageService;

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
        School findUserScool = userService.findSchoolByUserName(username);
        SchoolRaceStatus schoolRaceStatus = schoolService.findSchoolScoreRaced(
            findUserScool.getScore());
        schoolRaceStatus.addEntity(findUserScool);
        schoolRaceStatus.sortAroundEntitys();
        return schoolRaceStatus.toResponseDto(findUserScool,schoolRaceStatus.aroundUsers());
    }

    @Transactional(readOnly = true)
    public RegionRankRaceResponseDto makeUserRankRegionStatusByUsername(String username) {
        Region findRegion = userService.findRegionByUserName(username);
        RegionRaceStatus regionRaceStatus = regionService.findRegionScoreRaced(
            findRegion.getScore());
        regionRaceStatus.addEntity(findRegion);
        regionRaceStatus.sortAroundEntitys();
        return regionRaceStatus.toResponseDto(regionRaceStatus.aroundUsers());
    }

    @Transactional(readOnly = true)
    public List<UserRankMajorLanguageResponseDto> makeUserRankLanguageResponseByUsername(String username) {
        User findUser = userService.findByUserName(username);
        return majorLanguageService.findLanguagePercentByUser(findUser);
    }
}
