package gitbal.backend.domain.onedaycommit.application.repository;

import gitbal.backend.domain.onedaycommit.application.OneDayCommit;
import gitbal.backend.domain.onedaycommit.infra.OneDayCommitJpaEntity;

public interface OneDayCommitRepository {

    OneDayCommitJpaEntity save(OneDayCommit oneDayCommit);

}
