package gitbal.backend.domain.refreshtoken.application.repository;

import gitbal.backend.domain.refreshtoken.RefreshToken;
import java.util.Optional;

public interface RefreshTokenRepository {
    Optional<RefreshToken> findByUserNickname(String userNickname);

    void deleteByUserNickname(String userNickname);

    void deleteById(String id);

    void save(RefreshToken refreshToken);
}
