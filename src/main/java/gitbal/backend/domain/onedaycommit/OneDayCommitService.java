package gitbal.backend.domain.onedaycommit;

import gitbal.backend.domain.onedaycommit.application.OneDayCommit;
import gitbal.backend.domain.onedaycommit.application.repository.OneDayCommitRepository;
import gitbal.backend.domain.onedaycommit.infra.OneDayCommitJpaEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OneDayCommitService {


    private final OneDayCommitRepository oneDayCommitRepository;


    // TODO : CommitDate 관련 설정 정책 다시 생각해보기! -> Commit 이 아니라 Contribution 으로 진행해야 할 듯
    public OneDayCommitJpaEntity calculateRecentCommit(boolean hasYesterdayCommit) {
        return oneDayCommitRepository.save(OneDayCommit.of(hasYesterdayCommit));
    }

    public void updateCommit(OneDayCommitJpaEntity oneDayCommitJpaEntity, Boolean recentCommit) {
        log.info("inUpdateCommit");
        log.info("isRecentCommit {}", recentCommit);
        oneDayCommitJpaEntity.updateOneDayCommitSteadyCount(recentCommit);
    }
}
