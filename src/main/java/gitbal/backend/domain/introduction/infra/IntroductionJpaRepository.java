package gitbal.backend.domain.introduction.infra;

import gitbal.backend.domain.introduction.Introduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface IntroductionJpaRepository extends JpaRepository<Introduction, Long> {
}
