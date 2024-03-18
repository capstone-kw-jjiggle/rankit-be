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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="region_id")
    private Long regionId;



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
