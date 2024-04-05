package gitbal.backend.entity.dto;

public record UserRankRaceResponseDto(String username, String imgUrl) {

    public static UserRankRaceResponseDto of(String username, String imgUrl) {
        return new UserRankRaceResponseDto(username,imgUrl);
    }


}
