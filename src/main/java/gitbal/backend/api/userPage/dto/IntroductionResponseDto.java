package gitbal.backend.api.userPage.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IntroductionResponseDto {
  public String oneLiner;
  public String goodAt;
  public String learningGoal;

  public static IntroductionResponseDto of(String oneLiner, String goodAt, String learningGoal){
    return new IntroductionResponseDto(oneLiner, goodAt, learningGoal);
  }
}
