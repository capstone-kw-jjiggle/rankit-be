package gitbal.backend.service;

import gitbal.backend.domain.PageCalculator;
import gitbal.backend.entity.Region;
import gitbal.backend.entity.School;
import gitbal.backend.entity.User;
import gitbal.backend.entity.dto.MainPageFirstRankResponseDto;
import gitbal.backend.entity.dto.MainPageUserDto;
import gitbal.backend.entity.dto.MainPageUserResponseDto;
import gitbal.backend.entity.dto.PageInfoDto;
import gitbal.backend.exception.MainPageException;
import gitbal.backend.repository.RegionRepository;
import gitbal.backend.repository.SchoolRepository;
import gitbal.backend.repository.UserRepository;
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
            throw new MainPageException("올바른 page 값을 던져주시기 바랍니다 현재 페이지는: " + page + "입니다.");
        }
    }

    public MainPageUserResponseDto getSearchedUserList(String searchedname, int page) {
        if(page == 0){
            page=1;
        }try {
            Page<User> searchUsersIgnoreCase = userRepository.findByNicknameContainingIgnoreCase(searchedname,
                PageRequest.of(page - 1, PAGE_SIZE, Sort.by("score").descending()));
            validatePage(page, searchUsersIgnoreCase.getTotalElements());
            List<MainPageUserDto> searchUserList = searchUsersIgnoreCase.stream().map(
                (user) -> new MainPageUserDto(user.getNickname(), user.getScore(), user.getGrade())
            ).toList();
            PageInfoDto pageInfoDto = PageCalculator.calculatePageInfo(searchUsersIgnoreCase);
            return MainPageUserResponseDto.of(searchUserList, pageInfoDto);
        }catch (IllegalArgumentException e){
            throw new MainPageException("올바른 page 값을 던져주시기 바랍니다 현재 페이지는: " + page + "입니다.");
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
        Region region = regionRepository.firstRankedRegion();
        School school = schoolRepository.firstRankedSchool();
        return MainPageFirstRankResponseDto.of(school,region);
    }
}
