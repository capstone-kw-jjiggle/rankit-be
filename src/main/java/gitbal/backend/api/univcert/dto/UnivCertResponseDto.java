package gitbal.backend.api.univcert.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UnivCertResponseDto {
  private boolean success;
  private String message;

  public UnivCertResponseDto(boolean success, String message) {
    this.success = success;
    this.message = message;
  }

  public static UnivCertResponseDto of(boolean success) {
    if(success)
        return new UnivCertResponseDto(true, "인증에 성공했습니다.");
    else
        return new UnivCertResponseDto( false, "인증에 실패했습니다.");
  }
}
