package gitbal.backend.dto;

import gitbal.backend.domain.CommitDate;
import gitbal.backend.domain.MajorLanguage;
import gitbal.backend.domain.Region;
import gitbal.backend.domain.School;
import gitbal.backend.domain.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for {@link gitbal.backend.domain.User}
 */
@Getter
@Setter
public class UserDto {

    private School school;
    private Region region;
    private CommitDate commitDate;
    private List<MajorLanguage> majorLanguages;
    private String nickname;
    private Long pr_count;
    private Long commit_count;
    private String profile_img;


    public UserDto(School school, Region region, CommitDate commitDate,
        List<MajorLanguage> majorLanguages, String nickname, Long pr_count, Long commit_count,
        String profile_img) {
        this.school = school;
        this.region = region;
        this.commitDate = commitDate;
        this.majorLanguages = majorLanguages;
        this.nickname = nickname;
        this.pr_count = pr_count;
        this.commit_count = commit_count;
        this.profile_img = profile_img;
    }

    public static UserDto of(School school, Region region, CommitDate commitDate,
        List<MajorLanguage> majorLanguages, String nickname, Long pr_count, Long commit_count,
        String profile_img) {
        return UserDto.of(school, region, commitDate, majorLanguages, nickname, pr_count, commit_count, profile_img);
    }


    public static User toEntity(UserDto userDto) {
        return User.builder()
            .school(userDto.getSchool())
            .region(userDto.getRegion())
            .commitDate(userDto.getCommitDate())
            .majorLanguages(userDto.getMajorLanguages())
            .nickname(userDto.getNickname())
            .pr_count(userDto.getPr_count())
            .commit_count(userDto.getCommit_count())
            .profile_img(userDto.getProfile_img())
            .build();
    }
}