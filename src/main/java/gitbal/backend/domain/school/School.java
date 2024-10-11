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


  public School(String schoolName, Long score) {
    this.schoolName = schoolName;
    this.score = score;
  }

  public static School of(String schoolName, Long score) {
    return new School(schoolName, score);
  }

  public void addScore(Long score) {
    this.score += score;
  }


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

}
