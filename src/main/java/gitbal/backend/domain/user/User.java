package gitbal.backend.domain.user;


import gitbal.backend.domain.guestbook.GuestBook;
import gitbal.backend.domain.introduction.Introduction;
import gitbal.backend.global.BaseTimeEntity;
import gitbal.backend.domain.school.School;
import gitbal.backend.global.constant.Grade;
import gitbal.backend.domain.region.Region;
import gitbal.backend.global.security.GithubOAuth2UserInfo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
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
@Table(name = "user",
indexes ={
    @Index(name = "user_name_index", columnList = "nickname")
}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;


    private String majorLanguage;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<GuestBook> guestBooks = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Introduction introduction;

    @ColumnDefault(value = "'nothing'")
    private String nickname;

    @ColumnDefault("0")
    private Long score;

    @ColumnDefault(value = "'nothing'")
    private String profile_img;

    // 등급 관련 설정
    @Enumerated(value = EnumType.STRING)
    private Grade grade;

    @Column(name = "user_rank")
    @ColumnDefault("0")
    private int userRank;

    @ColumnDefault(value="'nothing'")
    private String refreshToken;

    @ColumnDefault("0")
    @Column(name = "first_logined")
    private Boolean firstLogined = false;

    public void toggleLogined() {
        this.firstLogined = true;
    }

    public void setGrade(Grade grade) { this.grade = grade; }

    public void setUserRank(int userRank) {this.userRank = userRank;}

    public void setIntroduction(Introduction introduction) {
        this.introduction = introduction;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public void setProfileImg(String profile_img) {
        this.profile_img = profile_img;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setMajorLanguage(
        String majorLanguage) {
        this.majorLanguage = majorLanguage;
    }

    @Builder
    public User(School school, Region region,
        String majorLanguage,Introduction introduction,
        String nickname, Long score, String profile_img, Grade grade,int userRank, boolean firstLogined) {
        this.school = school;
        this.region = region;
        this.majorLanguage = majorLanguage;
        this.introduction=introduction;
        this.nickname = nickname;
        this.score = score;
        this.profile_img = profile_img;
        this.grade = grade;
        this.userRank = userRank;
        this.firstLogined = firstLogined;
    }


    public void joinUpdateUser(School school, Region region,
        String majorLanguage,
        String nickname, String profile_img, int userRank, Introduction introduction) {
        this.school = school;
        this.region = region;
        this.majorLanguage = majorLanguage;
        this.nickname = nickname;
        this.profile_img = profile_img;
        this.userRank = userRank;
        this.introduction = introduction;
    }


    public void joinMockUpdateUser(School school, Region region,
                               String majorLanguage,
                               String nickname, Long score, String profile_img, int userRank, Introduction introduction) {
        this.school = school;
        this.region = region;
        this.majorLanguage = majorLanguage;
        this.nickname = nickname;
        this.score = score;
        this.profile_img = profile_img;
        this.userRank = userRank;
        this.introduction = introduction;
    }

    public void updateScore(Long score){
        this.score=score;
    }

    public static User of(String username, String avatarUrl) {
        return new User(null, null, null, Introduction.of(),username, 0L, avatarUrl, Grade.YELLOW, 0, false);
    }



    public void updateImage(GithubOAuth2UserInfo githubOAuth2UserInfo) {
        if(this.profile_img.equals(githubOAuth2UserInfo.getAvatarImgUrl()))  return;
        this.profile_img=githubOAuth2UserInfo.getAvatarImgUrl();
    }
}
