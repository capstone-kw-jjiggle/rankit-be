package gitbal.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commit_date_id")
    private Long id;


    private LocalDateTime currentCommitDate;

    @ColumnDefault(value = "0")
    private Long steadyCount;


    public Contribution(LocalDateTime currentCommitDate, Long steadyCount) {
        this.currentCommitDate = currentCommitDate;
        this.steadyCount = steadyCount;
    }

    // TODO: test 용도여서 나중에 실제로 값 넣으면 변경해야함.

    public static Contribution of() {
        return new Contribution(LocalDateTime.now(), 0L);
    }

}
