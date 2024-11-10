package gitbal.backend.api.userPage.dto;

public record UserRankExpResponseDto(int percent, Long score, Long remainScore){
    public static UserRankExpResponseDto of(int percent, Long score, Long remainScore){
        return new UserRankExpResponseDto(percent, score, remainScore);
    }
}
