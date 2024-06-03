package gitbal.backend.api.mainPage.service;

import gitbal.backend.api.mainPage.dto.MainPageFirstRankResponseDto;
import gitbal.backend.api.mainPage.dto.MainPageUserDto;
import gitbal.backend.api.mainPage.dto.MainPageUserResponseDto;
import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.region.RegionRepository;
import gitbal.backend.domain.school.School;
import gitbal.backend.domain.school.SchoolRepository;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserRepository;
import gitbal.backend.global.dto.PageInfoDto;
import gitbal.backend.global.exception.MainPageFirstRankException;
import gitbal.backend.global.exception.WrongPageNumberException;
import gitbal.backend.global.util.PageCalculator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainPageService {

    private final int PAGE_SIZE = 12;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;
    private final RegionRepository regionRepository;


    @Transactional(readOnly = true)
    public MainPageUserResponseDto getUsers(int page, String searchedname) {
        if (!Objects.isNull(searchedname))   return getSearchedUserList(searchedname, page);
        try {
            Page<User> users = userRepository.findAll(
                PageRequest.of(page - 1, PAGE_SIZE, Sort.by("score").descending()));
            validatePage(page, users.getTotalElements());
            log.info(String.valueOf(users.getTotalElements()));
            List<MainPageUserDto> userList = users.stream().map(
                    (user) -> new MainPageUserDto(user.getNickname(), user.getScore(),
                        user.getUserRank(), user.getGrade()))
                .toList();
            PageInfoDto pageInfoDto = PageCalculator.calculatePageInfo(users);
            return MainPageUserResponseDto.of(userList, pageInfoDto);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new WrongPageNumberException(page);
        }
    }

    private MainPageUserResponseDto getSearchedUserList(String searchedname, int page) {
        try {
            Page<User> searchUsersIgnoreCase = userRepository.findByNicknameContainingIgnoreCase(
                searchedname,
                PageRequest.of(page - 1, PAGE_SIZE, Sort.by("score").descending()));
            if(isSearchedUserNone(searchUsersIgnoreCase))
                return MainPageUserResponseDto.of(List.of(), new PageInfoDto(0, 0, 0, 0));
            validatePage(page, searchUsersIgnoreCase.getTotalElements());
            List<MainPageUserDto> searchUserList = searchUsersIgnoreCase.stream().map(
                (user) -> new MainPageUserDto(user.getNickname(), user.getScore(),
                    user.getUserRank(), user.getGrade())
            ).toList();
            PageInfoDto pageInfoDto = PageCalculator.calculatePageInfo(searchUsersIgnoreCase);
            return MainPageUserResponseDto.of(searchUserList, pageInfoDto);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new WrongPageNumberException(page);
        }
    }

    private boolean isSearchedUserNone(Page<User> searchUsersIgnoreCase) {
        return searchUsersIgnoreCase.getTotalElements() == 0;
    }


    private void validatePage(int pageNumber, long totalNumber) {
        if (checkRemainPage(pageNumber, totalNumber)) {
            return;
        }

        if (rangeCheck(pageNumber, totalNumber)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean rangeCheck(int pageNumber, long totalNumber) {
        return (long) pageNumber * PAGE_SIZE > totalNumber || pageNumber < 0;
    }

    private boolean checkRemainPage(int pageNumber, long totalNumber) {
        return (long) pageNumber * PAGE_SIZE - totalNumber < PAGE_SIZE;
    }


    @Transactional(readOnly = true)
    public MainPageFirstRankResponseDto getMainPageFirstSchoolRegionRank() {
        try {
            Region region = regionRepository.firstRankedRegion();
            School school = schoolRepository.firstRankedSchool();
            return MainPageFirstRankResponseDto.of(school, region);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MainPageFirstRankException();
        }
    }
}
