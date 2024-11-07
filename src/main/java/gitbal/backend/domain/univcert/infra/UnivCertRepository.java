package gitbal.backend.domain.univcert.infra;

import gitbal.backend.domain.univcert.UnivCertEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnivCertRepository extends JpaRepository<UnivCertEntity, Long> {

    Optional<UnivCertEntity> findByEmail(String mail);

    void deleteByEmail(String email);
}
