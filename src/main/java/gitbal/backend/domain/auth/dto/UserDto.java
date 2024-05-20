package gitbal.backend.domain.auth.dto;


import gitbal.backend.domain.entity.MajorLanguage;
import gitbal.backend.domain.entity.OneDayCommit;
import gitbal.backend.domain.entity.Region;
import gitbal.backend.domain.entity.School;
import gitbal.backend.domain.entity.User;
import java.util.List;

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
