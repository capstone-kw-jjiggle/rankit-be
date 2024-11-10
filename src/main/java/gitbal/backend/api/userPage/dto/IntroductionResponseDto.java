package gitbal.backend.api.userPage.dto;

public record IntroductionResponseDto(String title, String oneLiner, String goodAt, String learningGoal) {

  public static IntroductionResponseDto of(String title, String oneLiner, String goodAt, String learningGoal) {
    return new IntroductionResponseDto(title, oneLiner, goodAt, learningGoal);
  }
}
