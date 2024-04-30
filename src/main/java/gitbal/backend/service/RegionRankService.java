package gitbal.backend.service;

import gitbal.backend.entity.Region;
import gitbal.backend.entity.School;
import gitbal.backend.entity.dto.FirstRankRegionDto;
import gitbal.backend.entity.dto.FirstRankSchoolDto;
import gitbal.backend.entity.dto.RegionListDto;
import gitbal.backend.entity.dto.RegionListPageResponseDto;
import gitbal.backend.repository.RegionRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RegionRankService {

  private final RegionRepository regionRepository;

  public RegionRankService(RegionRepository regionRepository) {
    this.regionRepository = regionRepository;
  }

  public RegionListPageResponseDto<RegionListDto> getRegionList() {
    Sort sort = Sort.by("score").descending();
    int page = 1;
    Pageable pageable = PageRequest.of(page - 1, 10, sort);
    Page<Region> regionPage = regionRepository.findAll(pageable);

    List<RegionListDto> regionDtoList = regionPage.stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());

    // 3. PageResponseDto 생성
    return RegionListPageResponseDto.<RegionListDto>withALl()
        .regionList(regionDtoList)
        .build();
  }

  public FirstRankRegionDto getFirstRankRegionnfo() {
    Region firstRegion = regionRepository.firstRankedRegion(); // TODO: 우선 가장 높은 점수의 학교를 가져오는 쿼리로 가져옴. (나중엔 미리 점수별로 정렬해둘 것이므로 수정)
    return FirstRankRegionDto.builder()
        .regionName(firstRegion.getRegionName())
        .regionScore(firstRegion.getScore())
        .regionChangeScore(null)
        .mvpName(firstRegion.getTopContributor())
        .build();
  }

  private RegionListDto convertToDto(Region region) {
    return new RegionListDto(
        region.getRegionName(),
        region.getScore(),
        0L,
        region.getTopContributor()
    );

  }
}
