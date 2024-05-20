package gitbal.backend.global.util;

import gitbal.backend.global.dto.UserScoreInfoDto;
import org.springframework.stereotype.Component;

@Component
public class GitbalScoreCalculator {


    // TODO: 이거 상수로 분리할 지 조금 더 생각해보기
    private final Long COMMIT_WEIGHT = 1L;
    private final Long COMMITS_MEDIAN = 250L;
    private final Long ISSUE_WEIGHT = 3L;
    private final Long ISSUE_MEDIAN = 25L;
    private final Long FOLLOWER_WEIGHT = 1L;

    private final Long FOLLOWER_MEDIAN = 10L;
    private final Long PR_WEIGHT = 5L;

    private final Long PR_MEDIAN = 50L;
    private final Long REPO_WEIGHT = 3L;

    private final Long REPO_MEDIAN = 10L;


    public Long calculate(UserScoreInfoDto userScoreInfoDto) {

        Long commitCount = userScoreInfoDto.commitCount();
        Long followerCount = userScoreInfoDto.followerCount();
        Long issueCount = userScoreInfoDto.issues();
        Long prCount = userScoreInfoDto.prCount();
        Long repoCount = userScoreInfoDto.repoCount();

        double rankScore = COMMIT_WEIGHT * exponentialCDF((double) commitCount / COMMITS_MEDIAN) +
            PR_WEIGHT * exponentialCDF((double) prCount / PR_MEDIAN) +
            ISSUE_WEIGHT * exponentialCDF((double) issueCount / ISSUE_MEDIAN) +
            REPO_WEIGHT * exponentialCDF((double) repoCount / REPO_MEDIAN) +
            COMMIT_WEIGHT * exponentialCDF((double) commitCount / COMMITS_MEDIAN) +
            FOLLOWER_WEIGHT * logNormalCDF((double) followerCount / FOLLOWER_MEDIAN);

        return (long) (int) Math.floor(rankScore * 10000);
    }


    // 정규화 과정
    private double logNormalCDF(double x) {
        // approximation
        return x / (1 + x);
    }

    private double exponentialCDF(double x) {
        return 1 - Math.pow(2, -x);
    }
}
