package gitbal.backend.entity.dto;

public record RegionListDto(String regionlName, Long regionScore, Long regionChangedScore,
                            String topContributorName) {

  public static SchoolListDto of(String regionlName, Long regionScore, Long regionChangedScore,
      String topContributorName) {
    return new SchoolListDto(regionlName, regionScore, regionChangedScore, topContributorName);
  }
}