package gitbal.backend.global.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 60 * 10) // 초단위 10분
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {


    @Id
    private String refreshToken;
    @Indexed
    private String userNickname;


    @Builder
    public RefreshToken(String refreshToken, String userNickname) {
        this.refreshToken = refreshToken;
        this.userNickname = userNickname;
    }
}
