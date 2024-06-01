package gitbal.backend.api.univcert.service;

import com.univcert.api.UnivCert;
import gitbal.backend.api.univcert.dto.UnivCertCodeDto;
import gitbal.backend.api.univcert.dto.UnivCertStartDto;
import gitbal.backend.global.exception.UnivCertCodeException;
import gitbal.backend.global.exception.UnivCertStartException;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UnivService {

  @Value("${API-KEY}")
  private String apikey;

  public Map<String, Object> CertStart(UnivCertStartDto univCertStartDto){
    try {
      return UnivCert.certify(apikey, univCertStartDto.getEmail(),univCertStartDto.getUnivName(), true);
    } catch (Exception e) {
      throw new UnivCertStartException();
    }

  }

  public Map<String, Object> CertCode(UnivCertCodeDto univCertCodeDto) {
    try {
      return UnivCert.certifyCode(apikey, univCertCodeDto.getEmail(), univCertCodeDto.getUnivName(), univCertCodeDto.getCode());
    } catch (Exception e) {
      throw new UnivCertCodeException();
    }
  }
}

