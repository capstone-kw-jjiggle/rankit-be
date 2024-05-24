package gitbal.backend.domain.refreshtoken;

import gitbal.backend.global.exception.NotFoundRefreshTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;


    public void deleteByUsername(String username){
        RefreshToken refreshToken = refreshTokenRepository.findByUserNickname(username).orElseThrow(
            NotFoundRefreshTokenException::new);
        refreshTokenRepository.deleteById(refreshToken.getRefreshToken());
    }


}
