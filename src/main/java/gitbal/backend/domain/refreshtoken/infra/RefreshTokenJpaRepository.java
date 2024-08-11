package gitbal.backend.domain.refreshtoken.infra;


import gitbal.backend.domain.refreshtoken.RefreshToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenJpaRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByUserNickname(String userNickname);

    void deleteByUserNickname(String userNickname);

}
