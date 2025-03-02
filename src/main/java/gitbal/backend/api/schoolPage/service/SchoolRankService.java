package gitbal.backend.api.schoolPage.service;


import gitbal.backend.api.regionPage.dto.RegionListDto;
import gitbal.backend.api.schoolPage.dto.MySchoolInfoResponseDto;
import gitbal.backend.api.schoolPage.dto.SchoolListDto;
import gitbal.backend.api.schoolPage.dto.SchoolListPageResponseDto;
import gitbal.backend.api.schoolPage.dto.UserInfoBySchool;
import gitbal.backend.api.schoolPage.dto.UserPageListBySchoolResponseDto;
import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.school.School;
import gitbal.backend.domain.school.SchoolRepository;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserRepository;
import gitbal.backend.global.exception.NotFoundSchoolException;
import gitbal.backend.global.exception.NotFoundUserException;
import gitbal.backend.global.exception.NotLoginedException;
import gitbal.backend.global.exception.PageOutOfRangeException;
import gitbal.backend.global.exception.SchoolRankPageUserInfoBySchoolException;
import gitbal.backend.global.exception.WrongPageNumberException;
import gitbal.backend.global.security.CustomUserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SchoolRankService {

    private static final int PAGE_SIZE = 14;
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;

    @Autowired
    public SchoolRankService(SchoolRepository schoolRepository, UserRepository userRepository) {
        this.schoolRepository = schoolRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public SchoolListPageResponseDto<SchoolListDto> getSchoolList(Integer page,
                                                                  String searchedSchoolName) {
        if (!Objects.isNull(searchedSchoolName)) {
            log.info("searchedSchoolName : {}", searchedSchoolName);
            return getSearchedSchoolList(searchedSchoolName, page);
        }
        try {
            log.info("searchSchooolName : {}", searchedSchoolName);

            Pageable pageable = initpageable(page, "score");
            Page<School> schoolPage = schoolRepository.findAll(pageable);

            List<SchoolListDto> schoolDtoList = schoolPage.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());

            // PageResponseDto 생성
            SchoolListPageResponseDto<SchoolListDto> schoolList = SchoolListPageResponseDto.<SchoolListDto>withALl()
                    .schoolList(schoolDtoList)
                    .page(page)
                    .total(schoolPage.getTotalElements())
                    .build();
            // 페이지 범위 넘겼을때
            log.info("schoolList.getTotalPages() : {}", schoolList.getTotalPages());
            if (schoolList.getTotalPages() < page) {

                throw new PageOutOfRangeException();
            }
            return schoolList;

            //페이지로 음수 또는 문자열을 받았을때
        } catch (IllegalArgumentException e) {
            throw new PageOutOfRangeException();
        }


    }


    private SchoolListDto convertToDto(School school) {
        return new SchoolListDto(
                school.getSchoolName(),
                school.getScore(),
                school.getSchoolRank()
        );
    }

    @Transactional(readOnly = true)
    public MySchoolInfoResponseDto getMySchoolInfo(Authentication authentication) {
        if (authentication == null) {
            throw new NotLoginedException();
        }
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        String username = principal.getNickname();
        User user = userRepository.findByNickname(username).orElseThrow(
                NotFoundUserException::new
        );
        School school = user.getSchool();
        return MySchoolInfoResponseDto.of(school);
    }

    @Transactional(readOnly = true)
    public UserPageListBySchoolResponseDto getUserListBySchoolName(int page, String schoolName) {
        try {
            if (!isPresentSchoolName(schoolName))
                throw new NotFoundSchoolException();

            Pageable pageable = initpageable(page, "score");
            Page<User> userBySchoolName = userRepository.findUserBySchool_SchoolName(schoolName,
                    pageable);
            log.info("userBySchoolName : {}", userBySchoolName.getTotalPages());
            if (userBySchoolName.getTotalPages() > 0 && userBySchoolName.getTotalPages() < page)
                throw new PageOutOfRangeException();

            List<UserInfoBySchool> userInfoBySchools = convertPageByUserInfoBySchool(
                    userBySchoolName, page);
            log.info("userBySchoolName : {}",userInfoBySchools);

            return buildUserPageListBySchoolResponseDto(page, userInfoBySchools, userBySchoolName);
        } catch (Exception e) {
            log.info(e.getMessage());
            if (Objects.isNull(e.getMessage()))
                throw new SchoolRankPageUserInfoBySchoolException("학교 랭킹 페이지 유저 정보 조회 중 오류가 발생했습니다.");
            throw new SchoolRankPageUserInfoBySchoolException(e.getMessage());
        }
    }

    private boolean isPresentSchoolName(String schoolName) {
        return schoolRepository.findBySchoolName(schoolName).isPresent();
    }

    private List<UserInfoBySchool> convertPageByUserInfoBySchool(Page<User> userBySchoolName, int page) {
        List<User> users = userBySchoolName.get().toList();
        List<UserInfoBySchool> userInfoBySchools = new ArrayList<>();
        int startNum = (page-1) * PAGE_SIZE +1;
        for (int index = startNum; index < users.size()+startNum; index++) {
            User user = users.get(index-startNum);
            UserInfoBySchool userInfo = convertToUserInfoBySchool(user, index);
            userInfoBySchools.add(userInfo);
        }
        return userInfoBySchools;
    }

    private Pageable initpageable(int page, String sortProperties) {
        Sort sort = Sort.by(sortProperties).descending();
        return PageRequest.of(page - 1, PAGE_SIZE, sort);
    }

    private UserPageListBySchoolResponseDto buildUserPageListBySchoolResponseDto(int page,
                                                                                 List<UserInfoBySchool> userInfoBySchools, Page<User> userBySchoolName) {
        return UserPageListBySchoolResponseDto.withAll()
                .userInfoBySchools(userInfoBySchools)
                .page(page)
                .total(userBySchoolName.getTotalElements())
                .build();
    }


    private UserInfoBySchool convertToUserInfoBySchool(User user, int rank) {
        return new UserInfoBySchool(
                user.getNickname(),
                user.getProfile_img(),
                user.getScore(),
                rank
        );
    }


    private SchoolListPageResponseDto<SchoolListDto> getSearchedSchoolList(
            String searchedSchoolName,
            int page) {
        if (page == 0) {
            page = 1;
        }
        try {
            Page<School> schoolPage = schoolRepository.findBySchoolNameContainingIgnoreCase(
                    searchedSchoolName,
                    PageRequest.of(page - 1, PAGE_SIZE, Sort.by("score").descending()));
            if (isSearchedSchoolHasNothing(schoolPage)) {
                if (page > 1) {
                    throw new PageOutOfRangeException();
                }
                return SchoolListPageResponseDto.emptyList();
            }
            if (schoolPage.getTotalPages() < page) {
                throw new PageOutOfRangeException();
            }

            List<SchoolListDto> searchedSchoolList = schoolPage.stream()
                    .map(this::convertToDto)
                    .toList();
            log.info("searchedSchoolList : {}", searchedSchoolList);
            SchoolListPageResponseDto<SchoolListDto> schoolList = SchoolListPageResponseDto.<SchoolListDto>withALl()
                    .schoolList(searchedSchoolList)
                    .page(page)
                    .total(schoolPage.getTotalElements())
                    .build();

            log.info("schoolList.getTotalPages() : {}", schoolList.getTotalPages());


            return schoolList;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new WrongPageNumberException(page);
        }
    }


    private boolean isSearchedSchoolHasNothing(Page<School> searchUsersIgnoreCase) {
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


}
