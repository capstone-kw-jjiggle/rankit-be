package gitbal.backend.security.jwt;

import gitbal.backend.domain.TokenInfo;
import gitbal.backend.domain.User;
import gitbal.backend.repository.TokenInfoRepository;
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
    private final TokenInfoRepository tokenInfoRepository;

    private final Long ACCESS_EXPIRE_LENGTH = 1000L * 20;
    private final Long REFRESH_EXPIRE_LENGTH = 1000L * 120;


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
        try {
            JwtParser build = Jwts.parserBuilder().setSigningKey(key).build();
            build.parseClaimsJws(token);
            log.info("안녀하십니까!");
            return true;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다. 리프레시 토큰을 확인하여 재발급하겠습니다.");
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

    public String regenerateToken(String token) {
        TokenInfo tokenInfo = tokenInfoRepository.findByAccessToken(token)
            .orElseThrow(() -> new IllegalArgumentException("어세스 토큰으로 찾을 수 없었습니다."));

        Date now = new Date();
        Date validity = new Date(now.getTime() + ACCESS_EXPIRE_LENGTH);
        Claims claims = parseClaims(token);

        // TODO : 아래 과정 나중에 분리하기! 현재 너무 몰려있음!
        System.out.println("리프레쉬  토큰 상태에서 claims.getSubject() is = " + claims.getSubject());

        String regenerateToken = Jwts.builder()
            .signWith(SignatureAlgorithm.HS512, key)
            .setSubject(claims.getSubject())
            .setIssuer("kyhojun")
            .setIssuedAt(now)
            .setExpiration(validity)
            .compact();

        tokenInfo.setAccessToken(regenerateToken);
        tokenInfoRepository.save(tokenInfo);

        //위 코드 분리하기!

        return regenerateToken;
    }

    public boolean validateRefreshToken(String token) {
        try {
            TokenInfo tokenInfo = tokenInfoRepository.findByAccessToken(token)
                .orElseThrow(() -> new IllegalArgumentException("찾으려는 리프레시토큰은 없습니다."));
            JwtParser build = Jwts.parserBuilder().setSigningKey(key).build();
            build.parseClaimsJws(tokenInfo.getRefreshToken());
            log.info("안녀하십니까!");
            return true;
        } catch (IllegalArgumentException e) {
            log.info(e.getMessage());
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 리프레시 토큰입니다. 재발급하겠습니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalStateException e) {
            log.info("JWT 토큰이 잘못되었습니다");
        }
        return false;
    }
}
