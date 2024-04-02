package gitbal.backend.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;


    // Todo: 공부한 이후 수정 작업 계속 진행 (JPA 공부)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", unique = true)
    private School school;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", unique = true)
    private Region region;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commit_date_id", unique = true)
    private OneDayCommit oneDayCommit;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "major_id")
    private List<MajorLanguage> majorLanguages = new ArrayList<>();


    @ColumnDefault(value = "'nothing'")
    private String nickname;

    @ColumnDefault("0")
    private Long score;

    @ColumnDefault(value = "'nothing'")
    private String profile_img;


    @Builder
    public User(School school, Region region, OneDayCommit oneDayCommit,
        List<MajorLanguage> majorLanguages,
        String nickname, Long score, String profile_img) {
        this.school = school;
        this.region = region;
        this.oneDayCommit = oneDayCommit;
        this.majorLanguages = majorLanguages;
        this.nickname = nickname;
        this.score = score;
        this.profile_img = profile_img;
    }

    public void joinUpdateUser(School school, Region region, OneDayCommit oneDayCommit,
        List<MajorLanguage> majorLanguages,
        String nickname, Long score, String profile_img) {
        this.school = school;
        this.region = region;
        this.oneDayCommit = oneDayCommit;
        this.majorLanguages = majorLanguages;
        this.nickname = nickname;
        this.score = score;
        this.profile_img = profile_img;
    }


    public static User of(String nickname, String profile_imgUrl) {
        return new User(null, null, null, null, nickname, 0L,
            profile_imgUrl);
    }


}
