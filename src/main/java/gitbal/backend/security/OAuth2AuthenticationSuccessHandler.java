package gitbal.backend.security;

import gitbal.backend.domain.TokenInfo;
import gitbal.backend.repository.TokenInfoRepository;
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
    private final String REDIRECT_URL = "http://localhost:8080/api/v1/logincheck";
    private final String ACCESS_TOKEN_PREFIX = "accessToken";
    private final TokenInfoRepository tokenInfoRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException{

        GithubOAuth2UserInfo githubOAuth2UserInfo = changeGithubOAuth2UserInfo(
            authentication);

        // 여기에 jwt 토큰 생성하는 코드 제작
        // 이후 redirection
        String accessToken = jwtTokenProvider.createAccessToken(githubOAuth2UserInfo);

        //여기서 refresh를 redis에 저장!
        String refreshToken = jwtTokenProvider.createRefreshToken(githubOAuth2UserInfo);

        tokenInfoRepository.save(TokenInfo.builder().nickname(
                githubOAuth2UserInfo.getNickname())
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build()
        );

        String uriString = UriComponentsBuilder.fromUriString(REDIRECT_URL)
            .queryParam(ACCESS_TOKEN_PREFIX, accessToken)
            .build().toUriString();

        response.sendRedirect(uriString);
    }

    private static GithubOAuth2UserInfo changeGithubOAuth2UserInfo(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        log.info("oauthUser is = {}", oAuth2User.getAttributes());
        return GithubOAuth2UserInfo.of(
            oAuth2User.getAttributes());
    }
}
