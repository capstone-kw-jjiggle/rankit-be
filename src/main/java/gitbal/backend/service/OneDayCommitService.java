package gitbal.backend.service;

import gitbal.backend.entity.OneDayCommit;
import gitbal.backend.repository.OneDayCommitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OneDayCommitService {


    private final OneDayCommitRepository oneDayCommitRepository;


    // TODO : CommitDate 관련 설정 정책 다시 생각해보기! -> Commit 이 아니라 Contribution 으로 진행해야 할 듯
    public OneDayCommit calculateRecentCommit(boolean hasYesterdayCommit) {
        return oneDayCommitRepository.save(OneDayCommit.of(hasYesterdayCommit));
    }
}
