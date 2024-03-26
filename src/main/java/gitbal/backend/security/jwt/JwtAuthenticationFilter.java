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

    private final String FAILURE_LOGIN_REDIRECTED = "http://localhost:8080/api/v1/loginCheck";



    // TODO : accessToken으로만 인증 가능하게 함! -> refreshToken 따로 DB 설정을 하여서 발급받아서 재검증 로직 검토해야함
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String token = extractBearerToken(request);

        // accessToken 만료됨 -> refreshToken 확인 ->

        // Validation Access Token
        try {
            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info(authentication.getName() + "의 인증정보 저장");
            }
            else if(StringUtils.hasText(token) && !tokenProvider.validateToken(token) && tokenProvider.validateRefreshToken(token)){
                log.info("다시 로그인을 해야합니다! 리프레시 토큰을 확인한 후 재발급합니다.");
                String regenerateToken = tokenProvider.regenerateToken(token);

                Authentication authentication = tokenProvider.getAuthentication(
                    regenerateToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // 여기에다가 다시 재발급한 토큰을 알려줌 프론트에게
                response.setHeader("accessToken", regenerateToken);
                log.info("다시 발급받은 정보로 인증정보 {} 저장", authentication.getName());
            }
            else{
                log.info("유효한 JWT 토큰이 없습니다.");
            }
        }catch (RedisConnectionFailureException e){
            log.error("redis 연결에 오류가 발생했습니다.");
        }

        filterChain.doFilter(request, response);
    }


    private String extractBearerToken(HttpServletRequest request){

        String bearerToken = request.getHeader("Authorization");

        String urlToken = request.getQueryString();

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            System.out.println("header accessToken is =" + bearerToken.substring(7));
            return bearerToken.substring(7);
        }
        if(StringUtils.hasText(urlToken) && urlToken.startsWith("accessToken=")){
            System.out.println("url Token is =" + urlToken.substring(12));
            return urlToken.substring(12);
        }
        return null;
    }
}
