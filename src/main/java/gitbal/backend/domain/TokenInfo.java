package gitbal.backend.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "RefreshToken", timeToLive = 60 * 60 * 24 * 3)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenInfo {


    @Id
    private String nickname;

    @Indexed
    @Setter
    private String accessToken;

    private String refreshToken;


    @Builder
    public TokenInfo(String nickname, String accessToken, String refreshToken) {
        this.nickname = nickname;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
