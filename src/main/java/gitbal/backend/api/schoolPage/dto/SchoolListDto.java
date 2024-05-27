package gitbal.backend.api.schoolPage.dto;


import gitbal.backend.global.constant.SchoolGrade;

public record SchoolListDto(String schoolName, Long schoolScore, Long schoolChangedScore,
                            int schoolRank,
                            String topContributorName,
                            SchoolGrade schoolGrade
                            ) {

  public static SchoolListDto of(String schoolName, Long schoolScore,int schoolRank, Long schoolChangedScore,
      String topContributorName, SchoolGrade schoolGrade) {
    return new SchoolListDto(schoolName, schoolScore, schoolChangedScore, schoolRank, topContributorName, schoolGrade);
  }



}
