package gitbal.backend.entity.dto;


public record UserScoreInfoDto(Long prCount, Long commitCount, Long issues,
                               Long followerCount, Long repoCount) {
    public static UserScoreInfoDto of(Long prCount, Long commitCount, Long issues,
        Long followerCount, Long repoCount) {
        return new UserScoreInfoDto(prCount, commitCount, issues, followerCount, repoCount);
    }


}
