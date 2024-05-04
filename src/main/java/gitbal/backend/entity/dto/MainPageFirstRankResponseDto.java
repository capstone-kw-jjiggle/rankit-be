package gitbal.backend.entity.dto;

import gitbal.backend.entity.Region;
import gitbal.backend.entity.School;
import lombok.Getter;

@Getter
public class MainPageFirstRankResponseDto {

    private String firstSchoolName;
    private String firstRegionName;


    public MainPageFirstRankResponseDto(String firstSchoolName, String firstRegionName) {
        this.firstSchoolName = firstSchoolName;
        this.firstRegionName = firstRegionName;
    }

    public static MainPageFirstRankResponseDto of(School school, Region region) {
        return new MainPageFirstRankResponseDto(school.getSchoolName(), region.getRegionName());
    }
}
