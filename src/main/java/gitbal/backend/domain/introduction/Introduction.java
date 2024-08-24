package gitbal.backend.domain.introduction;


import gitbal.backend.api.userPage.dto.FriendSuggestDto;
import gitbal.backend.global.constant.Grade;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Introduction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String oneLiner = "나에 대해 한 줄로 설명해 주세요.";

  @Column(nullable = false)
  private String goodAt = "나에 대해 한 줄로 설명해 주세요.";

  @Column(nullable = false)
  private String learningGoal = "나에 대해 한 줄로 설명해 주세요.";


  public static Introduction of(Long id, String oneLiner, String goodAt, String learningGoal) {
    return new Introduction(id, oneLiner, goodAt, learningGoal);
  }

}
