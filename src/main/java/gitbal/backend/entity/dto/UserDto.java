package gitbal.backend.entity.dto;

import gitbal.backend.entity.OneDayCommit;
import gitbal.backend.entity.MajorLanguage;
import gitbal.backend.entity.Region;
import gitbal.backend.entity.School;
import gitbal.backend.entity.User;
import java.util.List;

/**
 * DTO for {@link gitbal.backend.entity.User} as a record.
 */
public record UserDto(
    School school,
    Region region,
    OneDayCommit oneDayCommit,
    List<MajorLanguage> majorLanguages,
    String nickname,
    Long score,
    String profile_img) {

    public static UserDto of(School school, Region region, OneDayCommit oneDayCommit,
        List<MajorLanguage> majorLanguages, String nickname, Long score,
        String profile_img) {
        return new UserDto(school, region, oneDayCommit, majorLanguages, nickname, score, profile_img);
    }

    public static User toEntity(UserDto userDto) {
        return User.builder()
            .school(userDto.school())
            .region(userDto.region())
            .oneDayCommit(userDto.oneDayCommit())
            .majorLanguages(userDto.majorLanguages())
            .nickname(userDto.nickname())
            .score(userDto.score())
            .profile_img(userDto.profile_img())
            .build();
    }
}
