package gitbal.backend.domain.introduction;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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

  @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT '나에 대해 한줄로 소개 해주세요'")
  private String oneLiner = "나에 대해 한줄로 소개해 주세요.";

  @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT '자신 있는 기술에 대해 설명해주세요'")
  private String goodAt = "자신 있는 기술에 대해 설명해 주세요.";

  @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT '배우고 싶은 기술에 대해 설명해주세요'")
  private String learningGoal = "배우고 싶은 기술에 대해 설명해 주세요.";

}
