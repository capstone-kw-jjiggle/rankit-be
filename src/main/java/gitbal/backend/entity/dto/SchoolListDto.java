package gitbal.backend.entity.dto;


public record SchoolListDto(String schoolName, Long schoolScore, Long schoolChangedScore,
                            int schoolRank,
                            String topContributorName) {

  public static SchoolListDto of(String schoolName, Long schoolScore,int schoolRank, Long schoolChangedScore,
      String topContributorName) {
    return new SchoolListDto(schoolName, schoolScore, schoolChangedScore, schoolRank, topContributorName);
  }



}
