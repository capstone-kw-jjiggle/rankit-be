package gitbal.backend.global.dto;

import gitbal.backend.global.entity.School;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SchoolRankDto {

    private String schoolName;
    private Long schoolScore;
    private int schoolRank;
    private Boolean isUserSchool;


    public static SchoolRankDto of(School userSchool, School school) {
        return new SchoolRankDto(school.getSchoolName(), school.getScore(), school.getSchoolRank(),
            userSchool.getSchoolName().equals(school.getSchoolName()));
    }
}
