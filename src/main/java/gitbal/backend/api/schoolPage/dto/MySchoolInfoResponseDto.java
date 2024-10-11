package gitbal.backend.api.schoolPage.dto;

import gitbal.backend.domain.school.School;
import java.util.Objects;
import lombok.Getter;

@Getter
public class MySchoolInfoResponseDto {

    private final int mySchoolRank;
    private final String mySchoolName;
    private final long totalSchoolScore;


    public MySchoolInfoResponseDto(int mySchoolRank, String mySchoolName, long totalSchoolScore) {
        this.mySchoolRank = mySchoolRank;
        this.mySchoolName = mySchoolName;
        this.totalSchoolScore = totalSchoolScore;
    }

    public static MySchoolInfoResponseDto of(School school) {
        if(Objects.isNull(school))  return new MySchoolInfoResponseDto(0, null, 0);
        return new MySchoolInfoResponseDto(school.getSchoolRank(), school.getSchoolName(), school.getScore());
    }
}
