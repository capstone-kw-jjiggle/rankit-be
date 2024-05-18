package gitbal.backend.entity;

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
    private String userID;


    @Builder
    public RefreshToken(String refreshToken, String userID) {
        this.refreshToken = refreshToken;
        this.userID = userID;
    }
}
