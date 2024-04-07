package gitbal.backend.controller;

import gitbal.backend.entity.dto.UnivCertCodeDto;
import gitbal.backend.entity.dto.UnivCertStartDto;
import gitbal.backend.service.UnivService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
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
  @Operation(summary = "대학 인증 메일을 요청.", description = "대학 인증을 위해 요청하는 곳입니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "대학 인증 요청을 성공했습니다."),
      @ApiResponse(responseCode = "400", description = "대학 인증 요청을 실패했습니다.")
  })
  public ResponseEntity<?> univRequestCertificate(@RequestBody UnivCertStartDto univCertStartDto)
      throws IOException {
    return ResponseEntity.ok(univSerivce.CertStart(univCertStartDto));
  }


  @PostMapping("/validate")
  @Operation(summary = "메일로 받은 인증번호 검증", description = "메일로 받은 인증번호를 검증하는 곳입니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "인증번호 검증을 성공했습니다."),
      @ApiResponse(responseCode = "400", description = "인증번호 검증을 실패했습니다.")
  })
  public ResponseEntity<?> univCertNumValidate(@RequestBody UnivCertCodeDto univCertCodeDto)
      throws IOException {
    return ResponseEntity.ok(univSerivce.CertCode(univCertCodeDto));
  }


}

