package gitbal.backend.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtils {

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


}
