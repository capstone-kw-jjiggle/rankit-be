package gitbal.backend.domain.refreshtoken.infra;

import gitbal.backend.domain.refreshtoken.RefreshToken;
import gitbal.backend.domain.refreshtoken.application.repository.RefreshTokenRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Override
    public Optional<RefreshToken> findByUserNickname(String userNickname) {
        return refreshTokenJpaRepository.findByUserNickname(userNickname);
    }

    @Override
    public void deleteByUserNickname(String userNickname) {
        refreshTokenJpaRepository.deleteByUserNickname(userNickname);
    }

    @Override
    public void deleteById(String id) {
        refreshTokenJpaRepository.deleteById(id);
    }

    @Override
    public void save(RefreshToken refreshToken) {
        refreshTokenJpaRepository.save(refreshToken);
    }
}
