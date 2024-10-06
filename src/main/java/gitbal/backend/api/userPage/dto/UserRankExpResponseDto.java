package gitbal.backend.api.userPage.dto;

public record UserRankExpResponseDto(int percent){
    public static UserRankExpResponseDto of(int percent){
        return new UserRankExpResponseDto(percent);
    }
}
