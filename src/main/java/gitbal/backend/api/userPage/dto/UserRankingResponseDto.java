package gitbal.backend.api.userPage.dto;

import lombok.Getter;

@Getter
public class UserRankingResponseDto {

    private int userRank;

    public UserRankingResponseDto(int userRank) {
        this.userRank = userRank;
    }

    public static UserRankingResponseDto of(int userRank) {
        return new UserRankingResponseDto(userRank);
    }
}
