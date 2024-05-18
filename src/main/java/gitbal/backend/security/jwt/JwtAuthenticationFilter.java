package gitbal.backend.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtTokenProvider tokenProvider;
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String BEARER_PREFIX = "Bearer ";
    private final String URL_PREFIX = "accessToken=";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String token = extractAccessToken(request);

        try {
            if (isValidAccessToken(token)) {
                Authentication authentication = registerAuthenticationToContext(token);
                log.info("[doFilterInternal]" + authentication.getName() + "의 인증정보 저장");
            } else if (isValidRefreshToken(token)) {
                log.info("[doFilterInternal] 다시 로그인을 해야합니다! 리프레시 토큰을 확인한 후 재발급합니다.");
                String regenerateToken = tokenProvider.regenerateToken(token);
                Authentication authentication = registerAuthenticationToContext(regenerateToken);
                response.setHeader(AUTHORIZATION_HEADER, regenerateToken);
                log.info("[doFilterInternal] 다시 발급받은 정보로 인증정보 {} 저장", authentication.getName());
            } else {
                log.info("[doFilterInternal] 유효한 JWT 토큰이 없습니다.");
            }
        } catch (RedisConnectionFailureException e) {
            log.error("[doFilterInternal] redis 연결에 오류가 발생했습니다.");
        }

        filterChain.doFilter(request, response);
    }

    private boolean isValidRefreshToken(String token) {
        return StringUtils.hasText(token) && tokenProvider.validateRefreshToken(token);
    }

    private boolean isValidAccessToken(String token) {
        return StringUtils.hasText(token) && tokenProvider.validateToken(token);
    }


    private String extractAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        String urlToken = request.getQueryString();

        return checkUrlOrBearerToken(bearerToken, urlToken);
    }

    private String checkUrlOrBearerToken(String bearerToken, String urlToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            String token = bearerToken.split(BEARER_PREFIX)[1];
            log.info("[checkUrlOrBearerToken] header accessToken is =" + token);
            return token;
        }
        if (StringUtils.hasText(urlToken) && urlToken.startsWith(URL_PREFIX)) {
            String token = urlToken.split(URL_PREFIX)[1];
            log.info("[checkUrlOrBearerToken] url Token is =" + token);
            return token;
        }
        return null;
    }


    private Authentication registerAuthenticationToContext(String aceessToken) {
        Authentication authentication = tokenProvider.getAuthentication(
            aceessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }


}
