package gitbal.backend.domain.schoolPage.service;


import gitbal.backend.global.entity.School;
import gitbal.backend.global.entity.User;
import gitbal.backend.domain.schoolPage.dto.FirstRankSchoolDto;
import gitbal.backend.domain.schoolPage.dto.MySchoolInfoResponseDto;
import gitbal.backend.domain.schoolPage.dto.SchoolListDto;
import gitbal.backend.domain.schoolPage.dto.SchoolListPageResponseDto;
import gitbal.backend.global.exception.NotFoundSchoolException;
import gitbal.backend.global.exception.NotFoundUserException;
import gitbal.backend.global.exception.NotLoginedException;
import gitbal.backend.global.exception.PageOutOfRangeException;
import gitbal.backend.global.exception.WrongPageNumberException;
import gitbal.backend.domain.repository.SchoolRepository;
import gitbal.backend.domain.repository.UserRepository;
import gitbal.backend.global.security.CustomUserDetails;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SchoolRankService {

    private static final int PAGE_SIZE = 10;
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;

    @Autowired
    public SchoolRankService(SchoolRepository schoolRepository, UserRepository userRepository) {
        this.schoolRepository = schoolRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<SchoolListPageResponseDto<SchoolListDto>> getSchoolList(Integer page) {
        try{
            Sort sort = Sort.by("score").descending();
            Pageable pageable = PageRequest.of(page - 1, 10, sort);
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
            if(schoolList.getTotalPages() < page ) {
                throw new PageOutOfRangeException();
            }
            return ResponseEntity.ok(schoolList);

            //페이지로 음수 또는 문자열을 받았을때
        } catch (IllegalArgumentException e) {
            throw new PageOutOfRangeException();
        }


    }

    public ResponseEntity<FirstRankSchoolDto> getFirstRankSchoolInfo() {
        School firstSchool = schoolRepository.firstRankedSchool(); // TODO: 우선 가장 높은 점수의 학교를 가져오는 쿼리로 가져옴. (나중엔 미리 점수별로 정렬해둘 것이므로 수정)
        FirstRankSchoolDto FirstRankInfo = FirstRankSchoolDto.builder()
            .schoolName(firstSchool.getSchoolName())
            .schoolScore(firstSchool.getScore())
            .schoolChangeScore(firstSchool.getChangedScore())
            .mvpName(firstSchool.getTopContributor())
            .build();
        return ResponseEntity.ok(FirstRankInfo);
    }

    private SchoolListDto convertToDto(School school) {
        return new SchoolListDto(
            school.getSchoolName(),
            school.getScore(),
            school.getChangedScore(),
            school.getSchoolRank(),
            school.getTopContributor()
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
    public SchoolListPageResponseDto<SchoolListDto> getSearchedSchoolList(String searchedSchoolName,
        int page) {
            if(page == 0){
                page=1;
            }try {
            Page<School> schoolPage = schoolRepository.findBySchoolNameContainingIgnoreCase(
                searchedSchoolName,
                PageRequest.of(page - 1, PAGE_SIZE, Sort.by("score").descending()));
            validateSearchSchoolName(schoolPage);
            validatePage(page,schoolPage.getTotalElements());
            List<SchoolListDto> searchedSchoolList = schoolPage.stream()
                .map(this::convertToDto)
                .toList();

            return SchoolListPageResponseDto.<SchoolListDto>withALl()
                .schoolList(searchedSchoolList)
                .page(page)
                .total(schoolPage.getTotalElements())
                .build();
            }catch (IllegalArgumentException e){
                e.printStackTrace();
                throw new WrongPageNumberException(page);
            }
        }


    private void validateSearchSchoolName(Page<School> searchUsersIgnoreCase) {
        if(searchUsersIgnoreCase.getTotalElements()==0){
            throw new NotFoundSchoolException();
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





}
