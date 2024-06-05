package gitbal.backend.api.univcert.service;

import com.univcert.api.UnivCert;
import gitbal.backend.api.univcert.dto.UnivCertCodeDto;
import gitbal.backend.api.univcert.dto.UnivCertResponseDto;
import gitbal.backend.api.univcert.dto.UnivCertStartDto;
import gitbal.backend.global.exception.UnivCertCodeException;
import gitbal.backend.global.exception.UnivCertStartException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UnivService {

  @Value("${API-KEY}")
  private String apikey;

  public UnivCertResponseDto certStart(UnivCertStartDto univCertStartDto){
    try {
      Map<String, Object> response = UnivCert.certify(apikey, univCertStartDto.getEmail(), univCertStartDto.getUnivName(), true);
      return responsetoDto(response, "인증번호 전송 완료");
    } catch (Exception e) {
      throw new UnivCertStartException();
    }
  }

  public UnivCertResponseDto certCode(UnivCertCodeDto univCertCodeDto) {
    try {
      Map<String, Object> response = UnivCert.certifyCode(apikey, univCertCodeDto.getEmail(), univCertCodeDto.getUnivName(), univCertCodeDto.getCode());
      return responsetoDto(response, "인증 성공");

    } catch (Exception e) {
      throw new UnivCertCodeException();
    }
  }

  private UnivCertResponseDto responsetoDto(Map<String, Object> univCertResponse, String successMessage) {
    boolean success = Boolean.TRUE.equals(univCertResponse.get("success"));
    int status = success ? 200 : (Integer) univCertResponse.get("code");
    String message = success ? successMessage : (String) univCertResponse.get("message");

    return UnivCertResponseDto.builder()
        .status(status)
        .success(success)
        .message(message)
        .build();
  }
}
