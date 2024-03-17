package gitbal.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class CommitDate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "commit_date_id")
    private Long id;

    @OneToOne(mappedBy = "commitDate")
    private User user;


    private LocalDateTime currentCommitDate;

    @NotNull
    private Long steadyCount;




}
