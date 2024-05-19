package gitbal.backend.security;

import gitbal.backend.entity.RefreshToken;
import gitbal.backend.repository.RefreshTokenRepository;
import gitbal.backend.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    // TODO : 프론트 url 받아서 보내줘야함!
    private final String REDIRECT_URL = "http://localhost:5173/auth/school";
    private final String ACCESS_TOKEN_PREFIX = "accessToken";
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {

        GithubOAuth2UserInfo githubOAuth2UserInfo = changeGithubOAuth2UserInfo(
            authentication);

        // TODO: 여기 관련 부분 뒤로 넘길 수 없는지 관련하여 고민
        String accessToken = jwtTokenProvider.createAccessToken(githubOAuth2UserInfo);
        String refreshToken = jwtTokenProvider.createRefreshToken(githubOAuth2UserInfo);

        if (isUserEmptyRefreshToken(githubOAuth2UserInfo)) {
            log.info("[onAuthenticationSuccess] refreshtoken이 발견되지 않았기에 제작하고 있는것입니다.");
            refreshTokenRepository.save(RefreshToken.builder().userNickname(
                    githubOAuth2UserInfo.getNickname())
                .refreshToken(refreshToken)
                .build()
            );
        }

        String uriString = UriComponentsBuilder.fromUriString(REDIRECT_URL)
            .queryParam(ACCESS_TOKEN_PREFIX, accessToken)
            .build().toUriString();

        response.sendRedirect(uriString);
    }

    private boolean isUserEmptyRefreshToken(GithubOAuth2UserInfo githubOAuth2UserInfo) {

        if (refreshTokenRepository.findByUserNickname(githubOAuth2UserInfo.getNickname())
            .isEmpty()) {
            log.info("[isUserHasRefreshToken] userHas RefreshToken");
            return true;
        }
        log.info("[isUserHasRefreshToken] : {}",
            refreshTokenRepository.findByUserNickname(githubOAuth2UserInfo.getNickname()).get()
                .getUserNickname());

        return false;
    }

    private static GithubOAuth2UserInfo changeGithubOAuth2UserInfo(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        log.info("[changeGithubOAuth2UserInfo]oauthUser is = {}", oAuth2User.getAttributes());
        return GithubOAuth2UserInfo.of(
            oAuth2User.getAttributes());
    }
}
