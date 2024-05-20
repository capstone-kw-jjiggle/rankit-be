package gitbal.backend.domain.repository;


import gitbal.backend.domain.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByUserNickname(String userNickname);

}
