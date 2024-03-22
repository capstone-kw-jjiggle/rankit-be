package gitbal.backend.security.jwt;

import gitbal.backend.domain.User;
import gitbal.backend.repository.UserRepository;
import gitbal.backend.security.CustomUserDetails;
import gitbal.backend.security.GithubOAuth2UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.key}")
    private String key;

    private final UserRepository userRepository;

    private final Long ACCESS_EXPIRE_LENGTH = 1000L * 60 * 60;
    private final Long REFRESH_EXPIRE_LENGTH = 1000L * 60 * 60 * 24;




    public String createAccessToken(GithubOAuth2UserInfo authentication) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + ACCESS_EXPIRE_LENGTH);

        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS512, key)
            .setSubject(authentication.getNickname())
            .setIssuer("kyhojun")
            .setIssuedAt(now)
            .setExpiration(validity)
            .compact();

    }

    public String createRefreshToken(GithubOAuth2UserInfo authentication
        ) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + REFRESH_EXPIRE_LENGTH);

        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS512, key)
            .setSubject(authentication.getNickname())
            .setIssuer("kyhojun")
            .setIssuedAt(now)
            .setExpiration(validity)
            .compact();
    }


    public boolean validateToken(String token) {
        try{
            JwtParser build = Jwts.parserBuilder().setSigningKey(key).build();
            build.parseClaimsJws(token);
            log.info("안녀하십니까!");
            return true;
        }catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalStateException e) {
            log.info("JWT 토큰이 잘못되었습니다");
        }
        return false;
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        System.out.println("claims.getSubject() is = " + claims.getSubject());
        User user = userRepository.findByNickname(claims.getSubject())
            .orElseThrow(() -> new IllegalArgumentException("not found user"));
        CustomUserDetails principal = CustomUserDetails.create(user);
        return new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
    }


    private Claims parseClaims(String accessToken) {
        try {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(key).build();
            return parser.parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
