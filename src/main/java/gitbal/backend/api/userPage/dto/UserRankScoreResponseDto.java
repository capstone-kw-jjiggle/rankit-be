package gitbal.backend.api.userPage.dto;

public record UserRankScoreResponseDto(Long userScore) {
    public static UserRankScoreResponseDto of(Long userScore) {
        return new UserRankScoreResponseDto(userScore);
    }
}
