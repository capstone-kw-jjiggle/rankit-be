package gitbal.backend.domain.refreshtoken;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gitbal.backend.global.exception.NotFoundRefreshTokenException;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @Test
    @DisplayName("리프레시 토큰 삭제 로직")
    void deleteByUsername(){
        // given
        String username = "test";
        RefreshToken testToken = mock(RefreshToken.class);

        // when
        when(refreshTokenRepository.findByUserNickname(username)).thenReturn(
            Optional.of(testToken));
        when(testToken.getRefreshToken()).thenReturn("testToken");
        refreshTokenService.deleteByUsername(username);

        // then
        verify(refreshTokenRepository, times(1)).deleteById(any(String.class));
    }


    @Test
    @DisplayName("리프레시 토큰 못 찾았을때 예외 발생")
    void deleteByNotFoundUsername(){
        // given
        String username = "test";

        // when
        when(refreshTokenRepository.findByUserNickname(username)).thenThrow(new NotFoundRefreshTokenException());

        // then
        Assertions.assertThatThrownBy(() -> refreshTokenService.deleteByUsername(username))
            .isInstanceOf(NotFoundRefreshTokenException.class);
    }

}