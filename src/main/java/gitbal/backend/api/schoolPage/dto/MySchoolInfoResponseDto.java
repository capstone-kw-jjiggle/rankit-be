package gitbal.backend.api.schoolPage.dto;

import gitbal.backend.domain.school.School;
import gitbal.backend.domain.school.SchoolService;
import gitbal.backend.global.constant.SchoolGrade;
import java.util.Objects;
import lombok.Getter;

@Getter
public class MySchoolInfoResponseDto {

    private final int mySchoolRank;
    private final String mySchoolName;
    private final long totalSchoolScore;
    private final String mvpName;
    private final long mvpTotalScore;
    private final SchoolGrade mySchoolGrade;

    public MySchoolInfoResponseDto(int mySchoolRank, String mySchoolName, long totalSchoolScore,
        String mvpName, long mvpTotalScore, SchoolGrade mySchoolGrade) {
        this.mySchoolRank = mySchoolRank;
        this.mySchoolName = mySchoolName;
        this.totalSchoolScore = totalSchoolScore;
        this.mvpName = mvpName;
        this.mvpTotalScore = mvpTotalScore;
        this.mySchoolGrade = mySchoolGrade;
    }

    public static MySchoolInfoResponseDto of(School school) {
        if(Objects.isNull(school))  return new MySchoolInfoResponseDto(0, null, 0, null, 0, null);
        return new MySchoolInfoResponseDto(school.getSchoolRank(), school.getSchoolName(), school.getScore(),
            school.getTopContributor(), school.getContributorScore(), school.getGrade());
    }
}
