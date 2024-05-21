package gitbal.backend.domain.refreshtoken;


import gitbal.backend.domain.refreshtoken.RefreshToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByUserNickname(String userNickname);

}
