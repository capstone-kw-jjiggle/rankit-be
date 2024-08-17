package gitbal.backend.api.regionPage.dto;


public record RegionListDto(String regionlName, Long regionScore) {

  public static RegionListDto of(String regionName, Long regionScore) {
    return new RegionListDto(regionName, regionScore);
  }
}