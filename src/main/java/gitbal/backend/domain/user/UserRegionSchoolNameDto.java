package gitbal.backend.domain.user;

import lombok.Getter;

@Getter
public class UserRegionSchoolNameDto {

    private String regionName;
    private String schoolName;


    public UserRegionSchoolNameDto(String regionName, String schoolName) {
        this.regionName = regionName;
        this.schoolName = schoolName;
    }


    public static UserRegionSchoolNameDto of(String regionName, String schoolName) {
        return new UserRegionSchoolNameDto(regionName, schoolName);
    }
}
