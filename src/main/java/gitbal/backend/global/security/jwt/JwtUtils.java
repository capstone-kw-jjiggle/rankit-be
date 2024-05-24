package gitbal.backend.global.security.jwt;

import gitbal.backend.global.exception.NoTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtils {


    private static final String BEARER_PREFIX = "Bearer ";
    private static final String URL_PREFIX = "accessToken=";

    public static Claims parseClaims(String accessToken, String key) {
        try {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(key).build();
            return parser.parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public static String generateToken(String subject, Date now, Date validity, String key) {
        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS512, key)
            .setSubject(subject)
            .setIssuer("kyhojun")
            .setIssuedAt(now)
            .setExpiration(validity)
            .compact();
    }

    public static JwtParser generateJwtParser(String key) {
        return Jwts.parserBuilder().setSigningKey(key).build();
    }


    public static String extractBearerToken(HttpServletRequest request){
        String tokenHeader = request.getHeader("Authorization");
        if(StringUtils.hasText(tokenHeader)){
            return extractBearerToken(tokenHeader);
        }
        throw new NoTokenException();
    }

    private static String extractBearerToken(String bearerToken) {
        String token = bearerToken.split(BEARER_PREFIX)[1];
        log.info("[extractBearerToken] header accessToken is =" + token);
        return token;
    }


    private static String extractUrlToken(String urlToken) {
        String token = urlToken.split(URL_PREFIX)[1];
        log.info("[extractUrlToken] url Token is =" + token);
        return token;
    }

    public static String extractUrlOrBearerToken(String bearerToken, String urlToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return extractBearerToken(bearerToken);
        }
        if (StringUtils.hasText(urlToken) && urlToken.startsWith(URL_PREFIX)) {
            return extractUrlToken(urlToken);
        }
        return null;
    }
}
