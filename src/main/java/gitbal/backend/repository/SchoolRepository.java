package gitbal.backend.repository;

import gitbal.backend.entity.School;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School,Long> {

    Optional<School> findBySchoolName(String schoolName);
}
