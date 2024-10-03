package gitbal.backend.global.security.jwt;


import gitbal.backend.domain.user.User;
import gitbal.backend.global.security.CustomUserDetails;
import gitbal.backend.global.security.GithubOAuth2UserInfo;
import gitbal.backend.domain.user.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.MalformedJwtException;
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

    private final UserRepository userRepository;
    @Value("${jwt.key}")
    private String key;
    @Value("${ACCESS_TOKEN_VALIDITY_SECONDS}")
    private Long ACCESS_EXPIRE_LENGTH;
    @Value("${REFRESH_TOKEN_VALIDITY_SECONDS}")
    private Long REFRESH_EXPIRE_LENGTH;


    public String createAccessToken(String nickname) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + ACCESS_EXPIRE_LENGTH);
        return JwtUtils.generateToken(nickname, now, validity, key);
    }

    public String createRefreshToken(GithubOAuth2UserInfo authentication) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + REFRESH_EXPIRE_LENGTH);
        return JwtUtils.generateToken(authentication.getNickname(), now, validity, key);
    }


    public String createRefreshToken(String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + REFRESH_EXPIRE_LENGTH);
        return JwtUtils.generateToken(username, now, validity, key);
    }

    public boolean validateToken(String token) {
        try {
            JwtParser build = JwtUtils.generateJwtParser(key);
            build.parseClaimsJws(token);
            log.info("[validateToken] 현재 JWT 검증완료하였습니다!");
            return true;
        } catch (ExpiredJwtException e) {
            log.info("[validateToken] 만료된 JWT입니다. 리프레시 토큰을 확인하여 재발급하겠습니다.");
        } catch (UnsupportedJwtException e) {
            log.info("[validateToken] 지원되지 않는 JWT입니다.");
        } catch (IllegalStateException e) {
            log.info("[validateToken] JWT가 잘못되었습니다");
        }
        return false;
    }


    public Authentication getAuthentication(String accessToken) {
        Claims claims = JwtUtils.parseClaims(accessToken, key);
        log.info("[getAuthentication] claims.getSubject() is = " + claims.getSubject());
        User user = userRepository.findByNickname(claims.getSubject())
            .orElseThrow(() -> new IllegalArgumentException("not found user"));
        CustomUserDetails principal = CustomUserDetails.create(user);
        return new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
    }


    public String regenerateToken(String refreshToken) {
        //한 번 DB에 존재하는지 검증한다 -> 혹여나 DB에서 사라졌을수도 있기에
        String findRefreshToken = findDBRefreshToken(refreshToken);

        Date now = new Date();
        Date validity = new Date(now.getTime() + ACCESS_EXPIRE_LENGTH);
        Claims claims = JwtUtils.parseClaims(findRefreshToken, key);
        log.info("[regenerateToken] 리프레쉬 토큰 상태에서 claims.getSubject() is = " + claims.getSubject()); // 유저 id 추출
        return JwtUtils.generateToken(claims.getSubject(), now, validity, key);
    }

    private String findDBRefreshToken(String refreshToken) {
        return userRepository.findRefreshTokenByNickname(
                findUserNicknameByToken(refreshToken))
            .orElseThrow(
                () -> new IllegalArgumentException("[regenerateToken] 리프레쉬 토큰을 찾을 수 없습니다."));
    }

    public String findUserNicknameByToken(String token) {
        return JwtUtils.parseClaims(token, key).getSubject();
    }

    public boolean validateRefreshToken(String token) {
        try {
            String tokenInfo = findDBRefreshToken(token);
            JwtParser build = JwtUtils.generateJwtParser(key);
            build.parseClaimsJws(tokenInfo);
            log.info("[validateRefreshToken] 토큰 오류 발생 안함!");
            return true;
        } catch (IllegalArgumentException e) {
            log.info(e.getMessage());
        } catch (ExpiredJwtException e) {
            log.info("[validateRefreshToken] 만료된 JWT 리프레시 토큰입니다. 재발급하겠습니다.");
        } catch (UnsupportedJwtException e) {
            log.info("[validateRefreshToken] 지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalStateException e) {
            log.info("[validateRefreshToken] JWT 토큰이 잘못되었습니다");
        } catch(MalformedJwtException e){
            log.info("[validateRefreshToken] JWT 토큰 형식에 맞지 않는 문제가 있었습니다!");
        }
        return false;
    }
}
