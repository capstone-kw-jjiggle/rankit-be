package gitbal.backend.api.auth.dto;


import gitbal.backend.domain.introduction.Introduction;
import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.school.School;
import gitbal.backend.domain.user.User;

public record UserDto(
    School school,
    Region region,
    String majorLanguage,
    String nickname,
    String profile_img,

    Introduction introduction) {

    public static UserDto of(School school, Region region, String majorLanguage, String nickname,
        String profile_img, Introduction introduction) {
        return new UserDto(school, region, majorLanguage, nickname, profile_img, introduction);
    }

}
