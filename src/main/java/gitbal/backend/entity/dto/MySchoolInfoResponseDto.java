package gitbal.backend.entity.dto;

import gitbal.backend.entity.School;
import lombok.Getter;

@Getter
public class MySchoolInfoResponseDto {

    private final int mySchoolRank;
    private final String mySchoolName;
    private final long totalSchoolScore;
    private final String mvpName;
    private final long mvpTotalScore;

    public MySchoolInfoResponseDto(int mySchoolRank, String mySchoolName, long totalSchoolScore,
        String mvpName, long mvpTotalScore) {
        this.mySchoolRank = mySchoolRank;
        this.mySchoolName = mySchoolName;
        this.totalSchoolScore = totalSchoolScore;
        this.mvpName = mvpName;
        this.mvpTotalScore = mvpTotalScore;
    }

    public static MySchoolInfoResponseDto of(School school) {
        return new MySchoolInfoResponseDto(school.getSchoolRank(), school.getSchoolName(), school.getScore(),
            school.getTopContributor(), school.getContributorScore());
    }
}