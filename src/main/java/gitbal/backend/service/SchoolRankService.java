package gitbal.backend.service;

import gitbal.backend.entity.dto.MySchoolInfoResponseDto;
import gitbal.backend.entity.School;
import gitbal.backend.entity.User;
import gitbal.backend.entity.dto.FirstRankSchoolDto;
import gitbal.backend.entity.dto.SchoolListDto;
import gitbal.backend.entity.dto.SchoolListPageResponseDto;
import gitbal.backend.exception.NotFoundUserException;
import gitbal.backend.exception.NotLoginedException;
import gitbal.backend.repository.SchoolRepository;
import gitbal.backend.repository.UserRepository;
import gitbal.backend.security.CustomUserDetails;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SchoolRankService {

    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;

    @Autowired
    public SchoolRankService(SchoolRepository schoolRepository, UserRepository userRepository) {
        this.schoolRepository = schoolRepository;
        this.userRepository = userRepository;
    }

    public SchoolListPageResponseDto<SchoolListDto> getSchoolList(Integer page) {
        Sort sort = Sort.by("score").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<School> schoolPage = schoolRepository.findAll(pageable);

        List<SchoolListDto> schoolDtoList = schoolPage.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());

        // PageResponseDto 생성
        return SchoolListPageResponseDto.<SchoolListDto>withALl()
            .schoolList(schoolDtoList)
            .page(page)
            .total(schoolPage.getTotalElements())
            .build();
    }

    public FirstRankSchoolDto getFirstRankSchoolInfo() {
        School firstSchool = schoolRepository.firstRankedSchool(); // TODO: 우선 가장 높은 점수의 학교를 가져오는 쿼리로 가져옴. (나중엔 미리 점수별로 정렬해둘 것이므로 수정)
        return FirstRankSchoolDto.builder()
            .schoolName(firstSchool.getSchoolName())
            .schoolScore(firstSchool.getScore())
            .schoolChangeScore(null)
            .mvpName(firstSchool.getTopContributor())
            .build();
    }

    private SchoolListDto convertToDto(School school) {
        return new SchoolListDto(
            school.getSchoolName(),
            school.getScore(),
            0L,
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
        return MySchoolInfoResponseDto.of(schoolRepository.getSchoolRanking(school.getSchoolName()), school);
    }
}
