package gitbal.backend.domain.user;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserScoreInfoDto {

    private Long prCount;
    private Long commitCount;
    private Long issues;
    private Long followerCount;
    private Long repoCount;

    public static UserScoreInfoDto of(Long prCount, Long commitCount, Long issues,
        Long followerCount, Long repoCount) {
        return new UserScoreInfoDto(prCount, commitCount, issues, followerCount, repoCount);
    }


}
