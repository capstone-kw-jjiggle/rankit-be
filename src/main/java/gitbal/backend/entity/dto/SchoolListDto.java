package gitbal.backend.entity.dto;

import gitbal.backend.entity.User;

public record SchoolListDto(String schoolName, Long schoolScore, Long schoolChangedScore,
                            String topContributorName) {

  public static SchoolListDto of(String schoolName, Long schoolScore, Long schoolChangedScore,
      String topContributorName) {
    return new SchoolListDto(schoolName, schoolScore, schoolChangedScore, topContributorName);
  }



}
