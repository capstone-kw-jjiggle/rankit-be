package gitbal.backend.domain.onedaycommit.application;

import gitbal.backend.global.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class OneDayCommit extends BaseTimeEntity {

    private Long steadyCount;

    public OneDayCommit(Long steadyCount) {
        this.steadyCount = steadyCount;
    }

    // 초기 가입할 때 넣기위한 값
    public static OneDayCommit of(boolean hasRecent) {
        if (hasRecent) {
            return new OneDayCommit(1L);
        }
        return new OneDayCommit(0L);
    }

}


