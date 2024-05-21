package gitbal.backend.domain.user;


import gitbal.backend.domain.school.School;
import gitbal.backend.global.constant.Grade;
import gitbal.backend.domain.majorlanguage.MajorLanguage;
import gitbal.backend.domain.onedaycommit.OneDayCommit;
import gitbal.backend.domain.region.Region;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "commit_date_id")
    private OneDayCommit oneDayCommit;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "major_user_id")
    private List<MajorLanguage> majorLanguages = new ArrayList<>();


    @ColumnDefault(value = "'nothing'")
    private String nickname;

    @ColumnDefault("0")
    private Long score;

    @ColumnDefault(value = "'nothing'")
    private String profile_img;

    // 등급 관련 설정
    @Enumerated(value = EnumType.STRING)
    private Grade grade;

    public void setSchool(School school) {
        this.school = school;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public void setProfileImg(String profile_img) {
        this.profile_img = profile_img;
    }

    @Builder
    public User(School school, Region region, OneDayCommit oneDayCommit,
        List<MajorLanguage> majorLanguages,
        String nickname, Long score, String profile_img, Grade grade) {
        this.school = school;
        this.region = region;
        this.oneDayCommit = oneDayCommit;
        this.majorLanguages = majorLanguages;
        this.nickname = nickname;
        this.score = score;
        this.profile_img = profile_img;
        this.grade = grade;
    }


    public void joinUpdateUser(School school, Region region, OneDayCommit oneDayCommit,
        List<MajorLanguage> majorLanguages,
        String nickname, Long score, String profile_img, Grade grade) {
        this.school = school;
        this.region = region;
        this.oneDayCommit = oneDayCommit;
        this.majorLanguages = majorLanguages;
        this.nickname = nickname;
        this.score = score;
        this.profile_img = profile_img;
    }

    public static User of(String username, String avatarUrl) {
        return new User(null, null, null, null, username, 0L, avatarUrl, Grade.NEWBIE);
    }


}
