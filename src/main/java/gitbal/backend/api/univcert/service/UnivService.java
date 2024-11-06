package gitbal.backend.api.univcert.service;

import gitbal.backend.api.univcert.dto.UnivCertCodeDto;
import gitbal.backend.api.univcert.dto.UnivCertResponseDto;
import gitbal.backend.api.univcert.dto.UnivCertStartDto;
import gitbal.backend.api.univcert.dto.UnivMailResponseDto;
import gitbal.backend.domain.school.School;
import gitbal.backend.domain.school.SchoolService;
import gitbal.backend.domain.univcert.constant.UnivMail;
import gitbal.backend.global.exception.UnivCertCodeException;
import gitbal.backend.global.exception.WrongUnivDomainException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UnivService {

  @Value("${API-KEY}")
  private String apikey;
  private final SchoolService schoolService;
  private final MailService mailService;


  public UnivMailResponseDto certStart(UnivCertStartDto univCertStartDto){
    try {
      if(!UnivMail.isRightMail(univCertStartDto.getUnivName(), univCertStartDto.getEmail()))
        throw new WrongUnivDomainException();
      log.info("before rightMail");
      School bySchoolName = schoolService.findBySchoolName(univCertStartDto.getUnivName());
      log.info("before check Null");
      if(Objects.isNull(bySchoolName)) throw new WrongUnivDomainException();

      log.info("before sendMail");
      mailService.sendMail(univCertStartDto.getEmail());
      return UnivMailResponseDto.of("인증 메일 전송에 성공하였습니다.");
    } catch (Exception e) {
      e.printStackTrace();
      throw new WrongUnivDomainException();
    }
  }

  @Transactional
  public UnivCertResponseDto certCode(UnivCertCodeDto univCertCodeDto) {
    try {
      Boolean successStatus = mailService.certCode(univCertCodeDto.getEmail(),
          String.valueOf(univCertCodeDto.getCode()));
      if(!isSuccess(successStatus))
        return responsetoDto(false);
      mailService.clearEmail(univCertCodeDto.getEmail());
      log.info("now Verifying email");
      return responsetoDto(true);
    } catch (Exception e) {
      e.printStackTrace();
      throw new UnivCertCodeException();
    }
  }

  private boolean isSuccess(Boolean successStatus) {
    return successStatus;
  }


  private UnivCertResponseDto responsetoDto(boolean success) {
    return UnivCertResponseDto.of(success);
  }
}
