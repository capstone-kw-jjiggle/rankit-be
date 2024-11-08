package gitbal.backend.domain.region;

import gitbal.backend.global.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter //todo : MockDataGenerator에서 score값을 0으로 초기화하기 위한 세팅
public class Region extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "region_id")
  private Long regionId;


  @NotNull
  @Column(length = 40)
  private String regionName;

  @NotNull
  @ColumnDefault(value = "0")
  private Long score;


  public Region(String regionName, Long score) {
    this.regionName = regionName;
    this.score = score;
  }

  public void addScore(Long score) {
    this.score += score;
  }


  public void updateScore(Long oldScore, Long newScore) {
    this.score=this.score-oldScore+newScore;
  }

  public void minusScore(Long score) {
    this.score -= score;
  }
}
