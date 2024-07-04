package gitbal.backend.domain.onedaycommit.infra;

import gitbal.backend.domain.onedaycommit.application.OneDayCommit;
import gitbal.backend.domain.onedaycommit.application.repository.OneDayCommitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OneDayCommitRepositoryImpl implements OneDayCommitRepository {

    private final OneDayCommitJpaRepository oneDayCommitJpaRepository;

    @Override
    public OneDayCommitJpaEntity save(OneDayCommit oneDayCommit) {
        return oneDayCommitJpaRepository.save(OneDayCommitJpaEntity.from(oneDayCommit));
    }
}
