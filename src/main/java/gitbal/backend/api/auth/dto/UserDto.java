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
    Long score,
    String profile_img,

    Introduction introduction) {

    public static UserDto of(School school, Region region, String majorLanguage, String nickname, Long score,
        String profile_img, Introduction introduction) {
        return new UserDto(school, region, majorLanguage, nickname, score, profile_img, introduction);
    }

    public static User toEntity(UserDto userDto) {
        return User.builder()
            .school(userDto.school())
            .region(userDto.region())
            .majorLanguage(userDto.majorLanguage)
            .nickname(userDto.nickname())
            .score(userDto.score())
            .profile_img(userDto.profile_img())
            .build();
    }
}
