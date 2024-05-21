package gitbal.backend.domain.region;

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
public class Region {

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

  @NotNull
  private String topContributor;

  @NotNull
  private Long contributorScore;


  public Region(String regionName, Long score, String topContributor, Long contributorScore) {
    this.regionName = regionName;
    this.score = score;
    this.topContributor = topContributor;
    this.contributorScore = contributorScore;
  }

  public void addScore(Long score) {
    this.score += score;
  }


  // TODO: test 용도여서 나중에 실제로 값 넣으면 변경해야함.
  public static Region of() {
    return new Region("부산", 0L, "khyojun", 0L);
  }

  public void updateContributerInfo(String nickname, Long score) {
    if (this.contributorScore == null || this.contributorScore < score) {
      this.topContributor = nickname;
      this.contributorScore = score;
    }
  }


}
