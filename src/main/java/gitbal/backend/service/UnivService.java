package gitbal.backend.service;

import com.univcert.api.UnivCert;
import gitbal.backend.dto.UnivCertCodeDto;
import gitbal.backend.dto.UnivCertStartDto;
import java.io.IOException;
import java.util.Map;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UnivService {

  @Value("${univ-token}")
  private String apikey;

  public Map<String, Object> CertStart(UnivCertStartDto univCertStartDto) throws IOException {
    String email = univCertStartDto.getEmail();
    String univName = univCertStartDto.getUnivName();

    return UnivCert.certify(apikey, email, univName, true);
  }

  public Map<String, Object> CertCode(UnivCertCodeDto univCertCodeDto)
      throws IOException, JSONException {
    String email = univCertCodeDto.getEmail();
    String univName = univCertCodeDto.getUnivName();
    Integer code = univCertCodeDto.getCode();

    //TODO : certifiCode에서 보내주는 return값이 더 깔끔한데, 따로 형식을 만들어서 response를 줄건지
    /*
    Map<String, Object> jsonResponse = UnivCert.certifyCode(apikey, email, univName,code);
    boolean success = jsonResponse.containsKey("\"success\": true");

    if (success) {
      return ResponseEntity.status(HttpStatus.OK).body("인증번호 검증을 성공했습니다.");
    }else {
      JSONObject jsonObject = new JSONObject(jsonResponse);
      String message = jsonObject.getString("message");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

     */

    return UnivCert.certifyCode(apikey, email, univName, code);

  }
}

