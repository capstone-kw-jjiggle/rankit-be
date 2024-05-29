package gitbal.backend.domain.onedaycommit;

import gitbal.backend.domain.user.User;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OneDayCommitService {


    private final OneDayCommitRepository oneDayCommitRepository;


    // TODO : CommitDate 관련 설정 정책 다시 생각해보기! -> Commit 이 아니라 Contribution 으로 진행해야 할 듯
    public OneDayCommit calculateRecentCommit(boolean hasYesterdayCommit) {
        return oneDayCommitRepository.save(OneDayCommit.of(hasYesterdayCommit));
    }

    public void updateCommit(OneDayCommit oneDayCommit, Boolean recentCommit) {
        log.info("inUpdateCommit");
        log.info("isRecentCommit {}", recentCommit);
        oneDayCommit.updateOneDayCommitSteadyCount(recentCommit);
    }
}
