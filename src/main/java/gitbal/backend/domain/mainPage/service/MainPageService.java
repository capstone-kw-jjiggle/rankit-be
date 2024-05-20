package gitbal.backend.domain.mainPage.service;

import gitbal.backend.global.dto.PageInfoDto;
import gitbal.backend.global.entity.Region;
import gitbal.backend.global.entity.School;
import gitbal.backend.domain.mainPage.dto.MainPageFirstRankResponseDto;
import gitbal.backend.domain.mainPage.dto.MainPageUserDto;
import gitbal.backend.global.entity.User;
import gitbal.backend.domain.mainPage.dto.MainPageUserResponseDto;
import gitbal.backend.global.util.PageCalculator;

import gitbal.backend.global.exception.MainPageFirstRankException;
import gitbal.backend.global.exception.NotFoundUserException;
import gitbal.backend.global.exception.WrongPageNumberException;
import gitbal.backend.domain.repository.RegionRepository;
import gitbal.backend.domain.repository.SchoolRepository;
import gitbal.backend.domain.repository.UserRepository;
import java.util.List;
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
    public MainPageUserResponseDto getUsers(int page) {
        try {
            Page<User> users = userRepository.findAll(
                PageRequest.of(page - 1, PAGE_SIZE, Sort.by("score").descending()));
            validatePage(page, users.getTotalElements());
            log.info(String.valueOf(users.getTotalElements()));
            List<MainPageUserDto> userList = users.stream().map(
                    (user) -> new MainPageUserDto(user.getNickname(), user.getScore(), user.getGrade()))
                .toList();
            PageInfoDto pageInfoDto = PageCalculator.calculatePageInfo(users);
            return MainPageUserResponseDto.of(userList, pageInfoDto);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new WrongPageNumberException(page);
        }
    }

    public MainPageUserResponseDto getSearchedUserList(String searchedname, int page) {
        if(page == 0){
            page=1;
        }try {
            Page<User> searchUsersIgnoreCase = userRepository.findByNicknameContainingIgnoreCase(searchedname,
                PageRequest.of(page - 1, PAGE_SIZE, Sort.by("score").descending()));
            validateSearchUsername(searchUsersIgnoreCase);
            validatePage(page, searchUsersIgnoreCase.getTotalElements());
            List<MainPageUserDto> searchUserList = searchUsersIgnoreCase.stream().map(
                (user) -> new MainPageUserDto(user.getNickname(), user.getScore(), user.getGrade())
            ).toList();
            PageInfoDto pageInfoDto = PageCalculator.calculatePageInfo(searchUsersIgnoreCase);
            return MainPageUserResponseDto.of(searchUserList, pageInfoDto);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            throw new WrongPageNumberException(page);
        }
    }

    private void validateSearchUsername(Page<User> searchUsersIgnoreCase) {
        if(searchUsersIgnoreCase.getTotalElements()==0){
            throw new NotFoundUserException();
        }
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
            return MainPageFirstRankResponseDto.of(school,region);
        }catch (Exception e){
            e.printStackTrace();
            throw new MainPageFirstRankException();
        }
    }
}
