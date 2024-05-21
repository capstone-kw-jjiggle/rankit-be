package gitbal.backend.api.userPage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegionRankDto {


    private String regionName;
    private Long regionScore;

    public static RegionRankDto of(String regionName, Long regionScore) {
        return new RegionRankDto(regionName, regionScore);
    }
}
