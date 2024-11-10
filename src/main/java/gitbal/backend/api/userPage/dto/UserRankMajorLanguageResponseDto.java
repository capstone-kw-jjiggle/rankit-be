package gitbal.backend.api.userPage.dto;

import lombok.Getter;

@Getter
public class UserRankMajorLanguageResponseDto {

    private String langName;

    public UserRankMajorLanguageResponseDto(String langName) {
        this.langName = langName;
    }

    public static UserRankMajorLanguageResponseDto of(String langName) {
        return new UserRankMajorLanguageResponseDto(langName);
    }

}
