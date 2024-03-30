package gitbal.backend.repository;

import gitbal.backend.entity.CommitDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommitDateRepository extends JpaRepository<CommitDate, Long> {

}
