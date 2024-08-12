package gitbal.backend.api.userPage.dto;


import lombok.Getter;

@Getter
public class RegionRankResponseDto {

    private RegionRankDto regionRankDto;

    public RegionRankResponseDto(RegionRankDto regionRankDto) {
        this.regionRankDto = regionRankDto;
    }

    public static RegionRankResponseDto of(RegionRankDto regionRankDto) {
        return new RegionRankResponseDto(regionRankDto);
    }
}
