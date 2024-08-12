package gitbal.backend.api.userPage.dto;

import gitbal.backend.domain.school.School;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SchoolRankDto {

    private String schoolName;
    private int schoolRank;


    public static SchoolRankDto of(School userSchool) {
        return new SchoolRankDto(userSchool.getSchoolName(), userSchool.getSchoolRank());
    }
}
