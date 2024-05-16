package gitbal.backend.entity.dto;

import gitbal.backend.entity.School;
import java.util.Objects;
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
