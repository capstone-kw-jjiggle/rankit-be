package gitbal.backend.domain.schoolPage.dto;

import lombok.Builder;

@Builder
public record FirstRankSchoolDto(String schoolName, Long schoolScore, Long schoolChangeScore,
                                 String mvpName) {

  public static FirstRankSchoolDto of(String schoolName, Long schoolScore, Long schoolChangeScore,
      String mvpName) {
    return new FirstRankSchoolDto(schoolName, schoolScore, schoolChangeScore, mvpName);
  }

}
