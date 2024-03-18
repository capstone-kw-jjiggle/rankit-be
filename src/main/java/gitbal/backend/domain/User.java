package gitbal.backend.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;


    // Todo: 공부한 이후 수정 작업 계속 진행 (JPA 공부)
    @OneToOne
    @JoinColumn(name = "school_id", unique = true)
    private School school;

    @OneToOne
    @JoinColumn(name = "region_id", unique = true)
    private Region region;

    @OneToOne
    @JoinColumn(name = "commit_date_id", unique = true)
    private CommitDate commitDate;

    @OneToMany(mappedBy = "user")
    private List<MajorLanguage> majorLanguages;


    @NotNull
    private String nickname;

    @NotNull
    private Long pr_count;

    @NotNull
    private Long commit_count;

    private String profile_img;




}
