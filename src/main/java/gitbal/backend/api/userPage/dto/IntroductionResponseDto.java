package gitbal.backend.api.userPage.dto;

public record IntroductionResponseDto(String oneLiner, String goodAt, String learningGoal) {

  public static IntroductionResponseDto of(String oneLiner, String goodAt, String learningGoal) {
    return new IntroductionResponseDto(oneLiner, goodAt, learningGoal);
  }
}
