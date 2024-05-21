package gitbal.backend.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GitbalApiDto {

    private Long score;
    private Boolean recentCommit;
    public static GitbalApiDto of(Long score, Boolean recentCommit) {
        return new GitbalApiDto(score, recentCommit);
    }
}
