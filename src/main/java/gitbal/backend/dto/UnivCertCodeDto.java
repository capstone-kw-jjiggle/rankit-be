package gitbal.backend.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class UnivCertCodeDto {

  private String email;
  private String univName;
  private Integer code;

  @Builder
  UnivCertCodeDto(String email, String univName, Integer code) {
    this.email = email;
    this.univName = univName;
    this.code = code;
  }

}
