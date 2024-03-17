package gitbal.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Long id;

    @OneToOne(mappedBy = "school")
    private User user;


    @NotNull
    @Column(length = 40)
    private String schoolName;

    @NotNull
    private Long score;

    @NotNull
    private String topContributor;

    @NotNull
    private Long contributorScore;
}
