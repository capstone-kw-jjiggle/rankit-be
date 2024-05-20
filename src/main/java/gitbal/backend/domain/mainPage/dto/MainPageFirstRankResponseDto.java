package gitbal.backend.domain.mainPage.dto;


import gitbal.backend.global.entity.Region;
import gitbal.backend.global.entity.School;
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
