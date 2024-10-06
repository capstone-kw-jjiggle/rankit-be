package gitbal.backend.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GitbalApiDto {

    private Long score;
    public static GitbalApiDto of(Long score) {
        return new GitbalApiDto(score);
    }
}
