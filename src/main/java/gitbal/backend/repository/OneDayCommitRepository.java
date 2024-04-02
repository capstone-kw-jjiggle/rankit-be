package gitbal.backend.repository;

import gitbal.backend.entity.OneDayCommit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneDayCommitRepository extends JpaRepository<OneDayCommit, Long> {

}
