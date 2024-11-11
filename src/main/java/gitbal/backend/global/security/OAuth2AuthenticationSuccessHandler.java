package gitbal.backend.global.security;

import gitbal.backend.api.auth.service.AuthService;
import gitbal.backend.api.auth.service.LoginService;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserRepository;
import gitbal.backend.domain.user.UserService;
import gitbal.backend.global.exception.NotFoundUserException;
import gitbal.backend.global.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    private final UserRepository userRepository;
    @Value("${LOGIN_SUCCESS_REDIRECT_URL}")
    private String REDIRECT_URL;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final LoginService loginService;
    private final AuthService authService;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        log.info("[onAuthenticationSuccess] : OAuth2 로그");
        GithubOAuth2UserInfo githubOAuth2UserInfo = changeGithubOAuth2UserInfo(
            authentication);
        if (isUserEmptyRefreshToken(githubOAuth2UserInfo) || isUserInDatabase(githubOAuth2UserInfo)) {
            log.info("[onAuthenticationSuccess] refreshtoken이 발견되지 않았거나 초기 MockData로 인한 임시의 refreshToken을 제작하고 있는것입니다.");
            updateUser(githubOAuth2UserInfo);
            tokenRefresh(githubOAuth2UserInfo);
        }

        authService.earlyJoin(githubOAuth2UserInfo.getNickname());

        String url = loginService.madeRedirectUrl(githubOAuth2UserInfo.getNickname());


        log.info("redirect 보내기 직전");
        log.info("redirect url : {}", url);
        response.sendRedirect(url);
    }

    private void tokenRefresh(GithubOAuth2UserInfo githubOAuth2UserInfo) {
        String refreshToken = jwtTokenProvider.createRefreshToken(githubOAuth2UserInfo);

        User user = userRepository.findByNickname(
            githubOAuth2UserInfo.getNickname()).orElseThrow(NotFoundUserException::new);

        log.info("[tokenRefresh] : refreshToken {}", refreshToken);

        user.setRefreshToken(refreshToken);
    }

    private void updateUser(GithubOAuth2UserInfo githubOAuth2UserInfo) {
        log.info("[updateUser] : 다시 로그인 하여 userLogin 관련 업데이트 작업 진행");
        userService.updateUser(githubOAuth2UserInfo);
    }

    private boolean isUserInDatabase(GithubOAuth2UserInfo githubOAuth2UserInfo) {
        return !Objects.isNull(userService.findByUserName(githubOAuth2UserInfo.getNickname()));
    }

    private boolean isUserEmptyRefreshToken(GithubOAuth2UserInfo githubOAuth2UserInfo) {

        log.info("[isUserHasRefreshToken] : {}",
            userRepository.findRefreshTokenByNickname(githubOAuth2UserInfo.getNickname()).isPresent());

        if (userRepository.findRefreshTokenByNickname(githubOAuth2UserInfo.getNickname())
            .isEmpty()) {
            log.info("[isUserHasRefreshToken] userHas RefreshToken");
            return true;
        }

        return false;
    }

    private GithubOAuth2UserInfo changeGithubOAuth2UserInfo(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        log.info("[changeGithubOAuth2UserInfo]oauthUser is = {}", oAuth2User.getAttributes());
        return GithubOAuth2UserInfo.of(
            oAuth2User.getAttributes());
    }
}
