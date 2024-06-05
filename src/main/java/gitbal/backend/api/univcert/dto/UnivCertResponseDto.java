package gitbal.backend.api.univcert.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class UnivCertResponseDto {
  private int status;
  private boolean success;
  private String message;

  public UnivCertResponseDto(int status, boolean success, String message) {
    this.status = status;
    this.success = success;
    this.message = message;
  }
}
