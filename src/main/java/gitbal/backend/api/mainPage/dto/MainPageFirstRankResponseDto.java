package gitbal.backend.api.mainPage.dto;


import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.school.School;
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
