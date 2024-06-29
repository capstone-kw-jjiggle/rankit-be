package gitbal.backend.domain.school;

import gitbal.backend.global.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import gitbal.backend.global.constant.SchoolGrade;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class School extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "school_id")
  private Long id;


  @NotNull
  @Column(length = 40)
  private String schoolName;

  @ColumnDefault(value = "0")
  private Long score;


  @ColumnDefault(value= "0")
  private Long changedScore;

  @ColumnDefault(value = "0")
  private int schoolRank;

  @Enumerated(value = EnumType.STRING)
  private SchoolGrade grade;

  @NotNull
  private String topContributor;

  @NotNull
  private Long contributorScore;

  @Column(name = "prev_day_score")
  @ColumnDefault(value = "0")
  private Long prevDayScore;

  public School(String schoolName, Long score, String topContributor, Long contributorScore) {
    this.schoolName = schoolName;
    this.score = score;
    this.topContributor = topContributor;
    this.contributorScore = contributorScore;
  }

  // TODO: test 용도여서 나중에 실제로 값 넣으면 변경해야함.
  public static School of() {
    return new School("광운대학교", 0L, "khyojun", 0L);
  }

  public static School of(String schoolName, Long score, String topContributor, Long contributorScore) {
    return new School(schoolName, score, topContributor, contributorScore);
  }


  public void addScore(Long score) {
    this.score += score;
  }

  public void updateContributerInfo(String nickname, Long score) {
    if (this.contributorScore == null || this.contributorScore < score) {
      this.topContributor = nickname;
      this.contributorScore = score;
    }
  }

  public void setGrade(SchoolGrade grade) { this.grade = grade; }

  public void setSchoolRank(int rank) {
    this.schoolRank=rank;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof School school)) {
      return false;
    }
    return Objects.equals(schoolName, school.schoolName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(schoolName);
  }

  public void updateScore(Long oldScore, Long newScore) {
    this.score= this.score-oldScore+newScore;
  }

  public void updatePrevDayScore(Long newScore) {
    this.prevDayScore = newScore;
  }

  public void updateChangedScore(Long newScore) {
    this.changedScore = newScore - this.prevDayScore;
  }
}
