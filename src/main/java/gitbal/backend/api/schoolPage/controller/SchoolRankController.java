package gitbal.backend.api.schoolPage.controller;


import gitbal.backend.api.schoolPage.dto.SchoolListPageResponseDto;
import gitbal.backend.api.schoolPage.dto.SchoolListDto;
import gitbal.backend.api.schoolPage.dto.MySchoolInfoResponseDto;
import gitbal.backend.api.schoolPage.dto.UserPageListBySchoolResponseDto;
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

    @GetMapping("/mySchool") // 삭제????
    @Operation(summary = "내 학교 정보 (구현만 완료 -> fe 와 api 통신해서 인증 확인 진행)", description = "내 학교 정보 탭 관련 api 요청입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "내 학교 정보 요청을 성공했습니다."),
        @ApiResponse(responseCode = "4xx", description = "내 학교 정보 요청을 실패했습니다.")
    })
    public ResponseEntity<MySchoolInfoResponseDto> mySchoolInfo(Authentication authentication) {
        return ResponseEntity.ok(schoolRankService.getMySchoolInfo(authentication));
    }
    @GetMapping("/schoolList") // 등수, 점수, 학교명만
    @Operation(summary = "학교 리스트 (8.17 수정-개발완료)", description = "학교 리스트 탭 관련 api 요청입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "학교 리스트 요청을 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "학교 리스트 요청을 실패했습니다.")
    })
    public ResponseEntity<SchoolListPageResponseDto<SchoolListDto>> schoolList(@RequestParam(defaultValue = "1", required = false) int page, @RequestParam(required = false) String searchedSchoolName) {
        return ResponseEntity.ok(schoolRankService.getSchoolList(page, searchedSchoolName));
    }

    @GetMapping("/userList") // 이름, 점수 , 등수는(list 순서대로 pageable 처리함)
    @Operation(summary = "학교별 유저 리스트 (8.25 개발완료)", description = "학교 리스트에 학교 클릭 후 유저들의 랭킹 리스트 api 요청입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "학교 별 유저 리스트 조회를 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "학교 별 유저 리스트 조회를 실패했습니다.")
    })
    public ResponseEntity<UserPageListBySchoolResponseDto> userListBySchoolName(@RequestParam(defaultValue = "1", required = false) int page, @RequestParam String schoolName) {
        return ResponseEntity.ok(schoolRankService.getUserListBySchoolName(page, schoolName));
    }



}
