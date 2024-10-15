package gitbal.backend.api.regionPage.dto;


public record RegionListDto(String regionlName, Long regionScore, int regionRank) {

  public static RegionListDto of(String regionName, Long regionScore, int regionRank) {
    return new RegionListDto(regionName, regionScore, regionRank);
  }
}