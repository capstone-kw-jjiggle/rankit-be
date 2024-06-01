package gitbal.backend.api.univcert.controller;


import gitbal.backend.api.univcert.dto.UnivCertCodeDto;
import gitbal.backend.api.univcert.dto.UnivCertStartDto;
import gitbal.backend.api.univcert.service.UnivService;
import gitbal.backend.global.exception.UnivCertCodeException;
import gitbal.backend.global.exception.UnivCertStartException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/univ")
@RequiredArgsConstructor
@Tag(name = "대학인증 API(구현 완료)", description = "대학 인증 관련 api입니다.")
public class UnivController {


  private final UnivService univSerivce;


  @PostMapping("/certificate")
  @Operation(summary = "대학 인증 메일을 요청 (구현 완료)", description = "대학 인증을 위해 요청하는 곳입니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "대학 인증 요청을 성공했습니다."),
      @ApiResponse(responseCode = "400", description = "대학 인증 요청을 실패했습니다.")
  })
  public ResponseEntity<Map<String, Object>> univRequestCertificate(@RequestBody UnivCertStartDto univCertStartDto) {
    return ResponseEntity.ok(univSerivce.CertStart(univCertStartDto));
  }


  @PostMapping("/validate")
  @Operation(summary = "메일로 받은 인증번호 검증 (구현완료)", description = "메일로 받은 인증번호를 검증하는 곳입니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "인증번호 검증을 성공했습니다."),
      @ApiResponse(responseCode = "400", description = "인증번호 검증을 실패했습니다.")
  })
  public ResponseEntity<Map<String, Object>> univCertNumValidate(@RequestBody UnivCertCodeDto univCertCodeDto) {
    return ResponseEntity.ok(univSerivce.CertCode(univCertCodeDto));
  }

}

