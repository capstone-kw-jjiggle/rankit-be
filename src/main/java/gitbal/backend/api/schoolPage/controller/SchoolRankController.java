package gitbal.backend.api.schoolPage.controller;


import gitbal.backend.api.schoolPage.dto.FirstRankSchoolDto;
import gitbal.backend.api.schoolPage.dto.SchoolListPageResponseDto;
import gitbal.backend.api.schoolPage.dto.SchoolListDto;
import gitbal.backend.api.schoolPage.dto.MySchoolInfoResponseDto;
import gitbal.backend.api.schoolPage.service.SchoolRankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schoolRank")
@RequiredArgsConstructor
@Tag(name = "학교 랭킹 API(구현중)", description = "학교 랭킹페이지를 위한 api입니다.")
public class SchoolRankController {

    private final SchoolRankService schoolRankService;

    @GetMapping("/mySchool")
    @Operation(summary = "내 학교 정보 (구현만 완료 -> fe 와 api 통신해서 인증 확인 진행)", description = "내 학교 정보 탭 관련 api 요청입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "내 학교 정보 요청을 성공했습니다."),
        @ApiResponse(responseCode = "4xx", description = "내 학교 정보 요청을 실패했습니다.")
    })
    public ResponseEntity<MySchoolInfoResponseDto> mySchoolInfo(Authentication authentication) {
        return ResponseEntity.ok(schoolRankService.getMySchoolInfo(authentication));
    }


    @GetMapping("/firstSchool")
    @Operation(summary = "1등 학교 정보 (구현 완료)", description = "1등 학교 정보 탭 관련 api 요청입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "1등 학교 정보 요청을 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "1등 학교 정보 요청을 실패했습니다.")
    })
    public ResponseEntity<FirstRankSchoolDto> firstRankSchoolInfo() {
        return schoolRankService.getFirstRankSchoolInfo();
    }

    @GetMapping("/schoolList")
    @Operation(summary = "학교 리스트 (구현 완료)", description = "학교 리스트 탭 관련 api 요청입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "학교 리스트 요청을 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "학교 리스트 요청을 실패했습니다.")
    })
    public ResponseEntity<SchoolListPageResponseDto<SchoolListDto>> schoolList(@RequestParam(defaultValue = "1", required = false) int page, @RequestParam(required = false) String searchedSchoolName) {
        return ResponseEntity.ok(schoolRankService.getSchoolList(page, searchedSchoolName));
    }


}
