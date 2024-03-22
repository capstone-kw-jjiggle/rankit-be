package gitbal.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommitDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commit_date_id")
    private Long id;


    private LocalDateTime currentCommitDate;

    @NotNull
    private Long steadyCount;


    public CommitDate(LocalDateTime currentCommitDate, Long steadyCount) {
        this.currentCommitDate = currentCommitDate;
        this.steadyCount = steadyCount;
    }

    // TODO: test 용도여서 나중에 실제로 값 넣으면 변경해야함.

    public static CommitDate of() {
        return new CommitDate(LocalDateTime.now(), 0L);
    }

}
