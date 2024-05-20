package gitbal.backend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OneDayCommit {

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

}
