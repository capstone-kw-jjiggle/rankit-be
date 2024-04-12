package gitbal.backend.entity.dto;

import lombok.Getter;

@Getter
public class UserRankMajorLanguageResponseDto {

    private String langName;
    private double langPercent;

    public UserRankMajorLanguageResponseDto(String langName, double langPercent) {
        this.langName = langName;
        this.langPercent = langPercent;
    }

    public static UserRankMajorLanguageResponseDto of(String langName, double langPercent) {
        return new UserRankMajorLanguageResponseDto(langName, langPercent);
    }




}
