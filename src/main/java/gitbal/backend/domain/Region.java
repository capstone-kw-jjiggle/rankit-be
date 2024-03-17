package gitbal.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="region_id")
    private Long regionId;

    @OneToOne(mappedBy = "region")
    private User user;

    @NotNull
    @Column(length = 40)
    private String regionName;

    @NotNull
    private Long score;

    @NotNull
    private String topContributor;

    @NotNull
    private Long contributorScore;


}
