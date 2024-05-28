package gitbal.backend.domain.onedaycommit;

import gitbal.backend.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OneDayCommit extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "one_day_commit_id")
    private Long id;


    @ColumnDefault(value = "0")
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


    public void updateOneDayCommitSteadyCount(boolean hasRecent){
        if(hasRecent) {
            this.steadyCount += 1L;
            return;
        }
        this.steadyCount=0L;
    }

}
