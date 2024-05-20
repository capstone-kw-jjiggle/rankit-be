package gitbal.backend.domain.regionPage.dto;


public record RegionListDto(String regionlName, Long regionScore, Long regionChangedScore,
                            String topContributorName) {

  public static RegionListDto of(String regionlName, Long regionScore, Long regionChangedScore,
      String topContributorName) {
    return new RegionListDto(regionlName, regionScore, regionChangedScore, topContributorName);
  }
}