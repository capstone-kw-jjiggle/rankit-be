package gitbal.backend.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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



    // TODO : accessToken으로만 인증 가능하게 함! -> refreshToken 따로 DB 설정을 하여서 발급받아서 재검증 로직 검토해야함
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String token = extractBearerToken(request);

        // accessToken 만료됨 -> refreshToken 확인 ->

        // Validation Access Token
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info(authentication.getName() + "의 인증정보 저장");
        }
        else
        {
            log.info("유효한 JWT 토큰이 없습니다.");
        }

        filterChain.doFilter(request, response);
    }


    private String extractBearerToken(HttpServletRequest request){

        String bearerToken = request.getHeader("Authorization");

        String urlToken = request.getQueryString();

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        if(StringUtils.hasText(urlToken) && urlToken.startsWith("accessToken=")){
            System.out.println("url Token is =" + urlToken.substring(12));
            return urlToken.substring(12);
        }
        return null;
    }
}
