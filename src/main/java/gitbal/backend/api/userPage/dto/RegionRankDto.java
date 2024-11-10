package gitbal.backend.api.userPage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegionRankDto {


    private String regionName;
    private Integer regionRank;

    public static RegionRankDto of(String regionName, Integer regionRank) {
        return new RegionRankDto(regionName, regionRank);
    }
}
