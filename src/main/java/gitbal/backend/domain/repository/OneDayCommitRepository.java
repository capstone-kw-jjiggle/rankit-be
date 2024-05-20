package gitbal.backend.domain.repository;


import gitbal.backend.domain.entity.OneDayCommit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneDayCommitRepository extends JpaRepository<OneDayCommit, Long> {

}
