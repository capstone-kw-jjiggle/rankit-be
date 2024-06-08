package gitbal.backend.global.security;

import gitbal.backend.domain.refreshtoken.RefreshToken;
import gitbal.backend.domain.refreshtoken.RefreshTokenRepository;
import gitbal.backend.domain.user.UserService;
import gitbal.backend.global.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
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
    private final String REDIRECT_URL = "http://localhost:8080/api/v1/login/success";
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {

        GithubOAuth2UserInfo githubOAuth2UserInfo = changeGithubOAuth2UserInfo(
            authentication);

        // TODO: 여기 관련 부분 뒤로 넘길 수 없는지 관련하여 고민

        if (isUserEmptyRefreshToken(githubOAuth2UserInfo) || isUserInDatabase(githubOAuth2UserInfo)) {
            log.info("[onAuthenticationSuccess] refreshtoken이 발견되지 않았거나 초기 MockData로 인한 임시의 refreshToken을 제작하고 있는것입니다.");
            updateUser(githubOAuth2UserInfo);
            tokenRefresh(githubOAuth2UserInfo);
        }

        String uriString = UriComponentsBuilder.fromUriString(REDIRECT_URL)
            .queryParam("username", githubOAuth2UserInfo.getNickname())
            .build().toUriString();

        response.sendRedirect(uriString);
    }

    private void tokenRefresh(GithubOAuth2UserInfo githubOAuth2UserInfo) {
        String refreshToken = jwtTokenProvider.createRefreshToken(githubOAuth2UserInfo);
        refreshTokenRepository.save(RefreshToken.builder().userNickname(
                githubOAuth2UserInfo.getNickname())
            .refreshToken(refreshToken)
            .build()
        );
    }

    private void updateUser(GithubOAuth2UserInfo githubOAuth2UserInfo) {
        log.info("[updateUser] : 다시 로그인 하여 userLogin 관련 업데이트 작업 진행");
        userService.updateUser(githubOAuth2UserInfo);
    }

    private boolean isUserInDatabase(GithubOAuth2UserInfo githubOAuth2UserInfo) {
        return !Objects.isNull(userService.findByUserName(githubOAuth2UserInfo.getNickname()));
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

    private GithubOAuth2UserInfo changeGithubOAuth2UserInfo(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        log.info("[changeGithubOAuth2UserInfo]oauthUser is = {}", oAuth2User.getAttributes());
        return GithubOAuth2UserInfo.of(
            oAuth2User.getAttributes());
    }
}
