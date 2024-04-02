package gitbal.backend.entity.dto;


public record UserInfoDto(Long prCount, Long commitCount, Long issues,
                          Long followerCount, Long repoCount) {
    public static UserInfoDto of(Long prCount, Long commitCount, Long issues,
        Long followerCount, Long repoCount) {
        return new UserInfoDto(prCount, commitCount, issues, followerCount, repoCount);
    }


}
