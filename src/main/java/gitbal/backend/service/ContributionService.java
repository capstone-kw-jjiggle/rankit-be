package gitbal.backend.service;

import gitbal.backend.entity.Contribution;
import gitbal.backend.repository.ContributionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContributionService {


    private final ContributionRepository contributionRepository;


    // TODO : CommitDate 관련 설정 정책 다시 생각해보기! -> Commit 이 아니라 Contribution 으로 진행해야 할 듯
    public Contribution calculateRecentCommit(String nickname) {
        Contribution save = contributionRepository.save(Contribution.of());
        return save;
    }
}
