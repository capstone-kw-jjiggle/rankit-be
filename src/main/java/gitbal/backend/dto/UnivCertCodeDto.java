package gitbal.backend.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class UnivDto {
  private String email;

  private String univName;

  @Builder
  UnivDto (String email, String univName) {
    this.email = email;
    this.univName = univName;
  }

}
