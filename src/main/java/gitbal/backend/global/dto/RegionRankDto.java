package gitbal.backend.global.dto;

public record RegionRankDto(String regionName, Long regionScore) {

    public static RegionRankDto of(String regionName, Long regionScore) {
        return new RegionRankDto(regionName, regionScore);
    }
}
