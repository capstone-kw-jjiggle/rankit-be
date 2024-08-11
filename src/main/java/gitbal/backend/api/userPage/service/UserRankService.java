package gitbal.backend.api.userPage.service;

import gitbal.backend.api.userPage.dto.RegionRankRaceResponseDto;
import gitbal.backend.api.userPage.dto.SchoolRankRaceResponseDto;
import gitbal.backend.api.userPage.dto.UserRankMajorLanguageResponseDto;
import gitbal.backend.api.userPage.dto.UserRankRaceResponseDto;
import gitbal.backend.api.userPage.dto.UserRankScoreResponseDto;
import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.school.School;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.majorlanguage.application.MajorLanguageService;
import gitbal.backend.domain.region.application.RegionService;
import gitbal.backend.domain.school.SchoolService;
import gitbal.backend.domain.user.UserService;
import gitbal.backend.domain.region.application.RegionRaceStatus;
import gitbal.backend.domain.school.SchoolRaceStatus;
import gitbal.backend.domain.user.UserRaceStatus;

import java.util.List;
import java.util.Objects;
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
        return userRaceStatus.toResponseDto(userRaceStatus.getAroundUsers());
    }


    @Transactional(readOnly = true)
    public SchoolRankRaceResponseDto makeUserRankSchoolStatusByUsername(String username) {
        School findUserScool = userService.findSchoolByUserName(username);
        if(Objects.isNull(findUserScool)) return SchoolRankRaceResponseDto.of(null);
        SchoolRaceStatus schoolRaceStatus = schoolService.findSchoolScoreRaced(
            findUserScool.getScore());
        schoolRaceStatus.addEntity(findUserScool);
        schoolRaceStatus.sortAroundEntitys();
        return schoolRaceStatus.toResponseDto(findUserScool,schoolRaceStatus.getAroundUsers());
    }

    @Transactional(readOnly = true)
    public RegionRankRaceResponseDto makeUserRankRegionStatusByUsername(String username) {
        Region findRegion = userService.findRegionByUserName(username);
        if(Objects.isNull(findRegion)) return RegionRankRaceResponseDto.of(null);
        RegionRaceStatus regionRaceStatus = regionService.findRegionScoreRaced(
            findRegion.getScore());
        regionRaceStatus.addEntity(findRegion);
        regionRaceStatus.sortAroundEntitys();
        return regionRaceStatus.toResponseDto(regionRaceStatus.getAroundUsers());
    }

    @Transactional(readOnly = true)
    public List<UserRankMajorLanguageResponseDto> makeUserRankLanguageResponseByUsername(String username) {
        User findUser = userService.findByUserName(username);
        return majorLanguageService.findLanguagePercentByUser(findUser);
    }
}
