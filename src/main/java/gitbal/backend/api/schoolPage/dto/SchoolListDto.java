package gitbal.backend.api.schoolPage.dto;


import gitbal.backend.global.constant.SchoolGrade;

public record SchoolListDto(String schoolName, Long schoolScore,int schoolRank) {

  public static SchoolListDto of(String schoolName, Long schoolScore,int schoolRank) {
    return new SchoolListDto(schoolName, schoolScore, schoolRank);
  }



}
