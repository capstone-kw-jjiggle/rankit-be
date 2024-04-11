package gitbal.backend.entity.dto;


import java.util.List;
import lombok.Getter;

@Getter
public class RegionRankRaceResponseDto {
    private String userRegionName;
    private List<RegionRankDto> regionRankDtoList;

    public RegionRankRaceResponseDto(String userRegionName, List<RegionRankDto> regionRankDtoList) {
        this.userRegionName = userRegionName;
        this.regionRankDtoList = regionRankDtoList;
    }

    public static RegionRankRaceResponseDto of(String userRegionName, List<RegionRankDto> regionRankDtoList) {
        return new RegionRankRaceResponseDto(userRegionName, regionRankDtoList);
    }
}
