package gitbal.backend.domain.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import gitbal.backend.global.exception.NotFoundUserException;
import gitbal.backend.global.util.SurroundingRankStatus;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private final int USER_AROUND_RANGE = 2;

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

    @Mock
    private UserScoreCalculator userScoreCalculator;
    @Mock
    private UserInfoService userInfoService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private UserService userService;


    @Test
    @DisplayName("유저 점수 계산 로직")
    void calculateUserScore() {
        // given
        String nickname = "testUser";
        String jsonResponse = getJsonResponse();

        Long calculateScore = calculateScore(UserScoreInfoDto.of(10L, 20L, 5L, 8L, 15L));
        when(userInfoService.requestUserInfo(nickname)).thenReturn(ResponseEntity.ok(jsonResponse));
        when(userScoreCalculator.calculate(any(UserScoreInfoDto.class))).thenReturn(
            calculateScore(UserScoreInfoDto.of(10L, 20L, 5L, 8L, 15L)));

        // when
        Long result = userService.calculateUserScore(nickname);

        // then
        assertThat(result).isEqualTo(calculateScore);
        verify(userInfoService, times(1)).requestUserInfo(nickname);
        verify(userScoreCalculator, times(1)).calculate(any(UserScoreInfoDto.class));
    }


    @Test
    @DisplayName("유저 점수 계산 로직 - json 오류")
    void calculateUserScoreErrorJson() {
        // given
        String nickname = "testUser";
        String jsonResponse = getInvalidJsonResponse();


        // when & then
        when(userInfoService.requestUserInfo(nickname)).thenReturn(ResponseEntity.ok(jsonResponse));
        assertThatThrownBy(() -> userService.calculateUserScore(nickname))
            .isInstanceOf(NotFoundUserException.class);
        verify(userInfoService, times(1)).requestUserInfo(nickname);
    }


    @Test
    @DisplayName("checkUserRecentCommit 성공 케이스 - 커밋 존재")
    void checkUserRecentCommit_success_commitExists() {
        // given
        String username = "testUser";
        String jsonResponse = """
            {
                "data": {
                    "user": {
                        "yesterdayCommits": {
                            "totalCommitContributions": 5
                        }
                    }
                }
            }
            """;

        when(userInfoService.requestUserRecentCommit(username)).thenReturn(ResponseEntity.ok(jsonResponse));

        // when
        boolean result = userService.checkUserRecentCommit(username);

        // then
        assertThat(result).isTrue();
        verify(userInfoService, times(1)).requestUserRecentCommit(username);
    }

    @Test
    @DisplayName("checkUserRecentCommit 성공 케이스 - 커밋 없음")
    void checkUserRecentCommit_success_noCommit() {
        // given
        String username = "testUser";
        String jsonResponse = """
            {
                "data": {
                    "user": {
                        "yesterdayCommits": {
                            "totalCommitContributions": 0
                        }
                    }
                }
            }
            """;


        when(userInfoService.requestUserRecentCommit(username)).thenReturn(ResponseEntity.ok(jsonResponse));

        // when
        boolean result = userService.checkUserRecentCommit(username);

        // then
        assertThat(result).isFalse();
        verify(userInfoService, times(1)).requestUserRecentCommit(username);
    }

    @Test
    @DisplayName("checkUserRecentCommit JsonProcessingException 예외 처리")
    void checkUserRecentCommit_jsonProcessingException() {
        // given
        String username = "testUser";
        String invalidResponse = """
            {
                "data": {
                    "user": {
                        "yesterdayCommits": {
                            "test":1
                        }
                    }
                }
            }
            """;

        when(userInfoService.requestUserRecentCommit(username)).thenReturn(ResponseEntity.ok(invalidResponse));


        // when & then
        assertThatThrownBy(() -> userService.checkUserRecentCommit(username))
            .isInstanceOf(RuntimeException.class);
        verify(userInfoService, times(1)).requestUserRecentCommit(username);
    }


    @Test
    @DisplayName("유저 점수랑 근처에 있는 사람 찾는 기능 관련 테스트")
    void findUsersScoreRaced(){
        Long score = 100L;
        int forwardCount = 10;
        int backwardCount = 5;
        when(userRepository.usersScoreRacedForward(score)).thenReturn(forwardCount);
        when(userRepository.userScoreRacedBackward(score)).thenReturn(backwardCount);

        SurroundingRankStatus surroundingRankStatus = SurroundingRankStatus.calculateSchoolRegionForwardBackward(
            forwardCount, backwardCount, USER_AROUND_RANGE);

        List<User> mockUsers = mock(List.class);
        when(userRepository.usersScoreRaced(score, surroundingRankStatus.getForwardCount(),
            surroundingRankStatus.getBackwardCount())).thenReturn(mockUsers);

        UserRaceStatus mockUserRaced = UserRaceStatus.of(mockUsers);

        UserRaceStatus userRaceStatus = userService.findUsersScoreRaced(score);

        Assertions.assertThat(userRaceStatus.getAroundUsers()).isEqualTo(mockUserRaced.getAroundUsers());
        verify(userRepository, times(1)).usersScoreRacedForward(score);
        verify(userRepository, times(1)).userScoreRacedBackward(score);
        verify(userRepository, times(1)).usersScoreRaced(score, surroundingRankStatus.getForwardCount(),
            surroundingRankStatus.getBackwardCount());
    }

    private String getInvalidJsonResponse(){
        return """
            {
                test:0
            }
            """;
    }




    private String getJsonResponse() {
        return """
            {
                "data": {
                    "user": {
                        "pullRequests": {
                            "totalCount": 10
                        },
                        "contributionsCollection": {
                            "totalCommitContributions": 20
                        },
                        "issues": {
                            "totalCount": 5
                        },
                        "followers": {
                            "totalCount": 8
                        },
                        "repositories": {
                            "totalCount": 15
                        }
                    }
                }
            }
            """;
    }


    private Long calculateScore(UserScoreInfoDto userScoreInfoDto) {
        Long commitCount = userScoreInfoDto.getCommitCount();
        Long followerCount = userScoreInfoDto.getFollowerCount();
        Long issueCount = userScoreInfoDto.getIssues();
        Long prCount = userScoreInfoDto.getPrCount();
        Long repoCount = userScoreInfoDto.getRepoCount();

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