package gitbal.backend.api.userPage.service;

import gitbal.backend.api.userPage.dto.RegionRankDto;
import gitbal.backend.api.userPage.dto.RegionRankResponseDto;
import gitbal.backend.api.userPage.dto.SchoolRankDto;
import gitbal.backend.api.userPage.dto.SchoolRankResponseDto;
import gitbal.backend.api.userPage.dto.UserRankExpResponseDto;
import gitbal.backend.api.userPage.dto.UserRankMajorLanguageResponseDto;
import gitbal.backend.api.userPage.dto.UserRankingResponseDto;
import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.school.School;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.majorlanguage.application.MajorLanguageService;
import gitbal.backend.domain.region.application.RegionService;
import gitbal.backend.domain.user.UserScoreCalculator;
import gitbal.backend.domain.user.UserService;

import gitbal.backend.global.constant.Grade;
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
    private final UserScoreCalculator userScoreCalculator;
    private final RegionService regionService;
    private final MajorLanguageService majorLanguageService;

    @Transactional(readOnly = true)
    public UserRankingResponseDto makeUserRankResponse(String username) {
        User findUser = userService.findByUserName(username);
        return UserRankingResponseDto.of(findUser.getUserRank());
    }



    @Transactional(readOnly = true)
    public SchoolRankResponseDto makeUserRankSchoolStatusByUsername(String username) {
        School findUserScool = userService.findSchoolByUserName(username);
        if(Objects.isNull(findUserScool)) return SchoolRankResponseDto.of(null);
        return SchoolRankResponseDto.of(SchoolRankDto.of(findUserScool));
    }

    @Transactional(readOnly = true)
    public RegionRankResponseDto makeUserRankRegionStatusByUsername(String username) {
        Region findRegion = userService.findRegionByUserName(username);
        if(Objects.isNull(findRegion)) return RegionRankResponseDto.of(null);
        int regionRanking = regionService.findRegionRanking(findRegion.getRegionName());
        return RegionRankResponseDto.of(RegionRankDto.of(findRegion.getRegionName(),  regionRanking));
    }

    @Transactional(readOnly = true)
    public UserRankMajorLanguageResponseDto makeUserRankLanguageResponseByUsername(String username) {
        User findUser = userService.findByUserName(username);
        return majorLanguageService.findMostUsageLanguageByUsername(findUser);
    }

    @Transactional(readOnly = true)
    public UserRankExpResponseDto makeUserRankExpResponse(String username) {
        User findUser = userService.findByUserName(username);
        return UserRankExpResponseDto.of(userService.
            calculateExp(findUser));
    }


}
