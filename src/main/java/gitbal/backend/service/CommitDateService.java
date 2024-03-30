package gitbal.backend.service;

import gitbal.backend.entity.CommitDate;
import gitbal.backend.repository.CommitDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommitDateService {


    private final CommitDateRepository commitDateRepository;


    // TODO : CommitDate 관련 설정 정책 다시 생각해보기! -> Commit 이 아니라 Contribution 으로 진행해야 할 듯
    public CommitDate calculateRecentCommit(String nickname) {
        CommitDate save = commitDateRepository.save(CommitDate.of());
        return save;
    }
}
