package gitbal.backend.domain.regionPage.dto;

import lombok.Builder;

@Builder
public record FirstRankRegionDto(String regionName, Long regionScore, Long regionChangeScore,
                                 String mvpName) {

  public static FirstRankRegionDto of(String regionName, Long regionScore, Long regionChangeScore,
      String mvpName) {
    return new FirstRankRegionDto(regionName, regionScore, regionChangeScore, mvpName);
  }
}
