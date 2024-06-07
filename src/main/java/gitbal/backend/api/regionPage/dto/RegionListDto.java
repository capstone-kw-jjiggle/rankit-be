package gitbal.backend.api.regionPage.dto;


public record RegionListDto(String regionlName, Long regionScore, Long regionChangedScore,
                            String topContributorName) {

  public static RegionListDto of(String regionName, Long regionScore, Long regionChangedScore,
      String topContributorName) {
    return new RegionListDto(regionName, regionScore, regionChangedScore, topContributorName);
  }
}