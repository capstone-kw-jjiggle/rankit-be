package gitbal.backend.api.univcert.service;

import com.univcert.api.UnivCert;
import gitbal.backend.api.univcert.dto.UnivCertCodeDto;
import gitbal.backend.api.univcert.dto.UnivCertResponseDto;
import gitbal.backend.api.univcert.dto.UnivCertStartDto;
import gitbal.backend.global.exception.UnivCertCodeException;
import gitbal.backend.global.exception.UnivCertStartException;
import java.io.IOException;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
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

      clearCertifiedList(response, univCertCodeDto.getEmail());

      return responsetoDto(response, "인증 성공");
    } catch (Exception e) {
      throw new UnivCertCodeException();
    }
  }

  private void clearCertifiedList(Map<String, Object> response, String email) throws IOException {
    Map<String, Object> clear = new HashMap<>();
    if(Boolean.TRUE.equals(response.get("success")))  clear = UnivCert.clear(apikey,email);
    else log.info("[clearResponse] 인증 과정에서 코드가 맞지 않아 clear하지 않았습니다.");
    if(Boolean.TRUE.equals(clear.get("success")))  log.info("[clearResponse] clear success");
    else log.error("[clearResponse] clear fail");
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
