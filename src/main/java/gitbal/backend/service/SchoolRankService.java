package gitbal.backend.service;

import gitbal.backend.entity.School;
import gitbal.backend.entity.dto.SchoolListDto;
import gitbal.backend.entity.dto.SchoolListPageResponseDto;
import gitbal.backend.repository.SchoolRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SchoolRankService {
  private final SchoolRepository schoolRepository;

  public SchoolRankService(SchoolRepository schoolRepository) {
    this.schoolRepository = schoolRepository;
  }

  public SchoolListPageResponseDto<SchoolListDto> getSchoolList(Integer page) {
    Sort sort = Sort.by("score").descending();
    Pageable pageable = PageRequest.of(page -1, 10, sort);
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
  private SchoolListDto convertToDto(School school) {
    return new SchoolListDto(
        school.getSchoolName(),
        school.getScore(),
        0L,
        school.getTopContributor()
    );

  }
}
