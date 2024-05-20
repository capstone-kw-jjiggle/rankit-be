package gitbal.backend.domain.dto;

public record RegionRankDto(String regionName, Long regionScore) {

    public static RegionRankDto of(String regionName, Long regionScore) {
        return new RegionRankDto(regionName, regionScore);
    }
}
