package gitbal.backend.api.userPage.dto;

import gitbal.backend.domain.school.School;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SchoolRankDto {

    private String schoolName;
    private Integer schoolRank;


    public static SchoolRankDto of(School userSchool) {
        if(Objects.isNull(userSchool)) return new SchoolRankDto(null, null);
        return new SchoolRankDto(userSchool.getSchoolName(), userSchool.getSchoolRank());
    }
}
