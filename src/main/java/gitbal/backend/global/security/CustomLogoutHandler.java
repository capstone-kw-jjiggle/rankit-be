package gitbal.backend.global.security;

import gitbal.backend.api.auth.service.AuthService;
import gitbal.backend.global.exception.LogoutException;
import gitbal.backend.global.security.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {

    @Value("${jwt.key}")
    private String jwtKey;
    private final AuthService authService;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) {
        String bearer = JwtUtils.extractBearerToken(request);
        log.info(bearer);
        Claims claims = JwtUtils.parseClaims(bearer, jwtKey);
        authService.logoutUser(claims.getSubject());
    }
}
