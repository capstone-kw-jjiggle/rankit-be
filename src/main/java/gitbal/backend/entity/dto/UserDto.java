package gitbal.backend.entity.dto;

import gitbal.backend.entity.CommitDate;
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
    CommitDate commitDate,
    List<MajorLanguage> majorLanguages,
    String nickname,
    Long pr_count,
    Long commit_count,
    String profile_img) {

    public static UserDto of(School school, Region region, CommitDate commitDate,
        List<MajorLanguage> majorLanguages, String nickname, Long pr_count, Long commit_count,
        String profile_img) {
        return new UserDto(school, region, commitDate, majorLanguages, nickname, pr_count, commit_count, profile_img);
    }

    public static User toEntity(UserDto userDto) {
        return User.builder()
            .school(userDto.school())
            .region(userDto.region())
            .commitDate(userDto.commitDate())
            .majorLanguages(userDto.majorLanguages())
            .nickname(userDto.nickname())
            .pr_count(userDto.pr_count())
            .commit_count(userDto.commit_count())
            .profile_img(userDto.profile_img())
            .build();
    }
}
