package gitbal.backend.api.userPage.controller;



import gitbal.backend.api.userPage.dto.RegionRankResponseDto;
import gitbal.backend.api.userPage.dto.SchoolRankResponseDto;
import gitbal.backend.api.userPage.dto.UserRankExpResponseDto;
import gitbal.backend.api.userPage.dto.UserRankMajorLanguageResponseDto;
import gitbal.backend.api.userPage.dto.UserRankingResponseDto;
import gitbal.backend.api.userPage.service.UserRankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/userRank")
@RequiredArgsConstructor
@Tag(name = "유저 순위 API", description = "유저 페이지의 rank탭 관련 api 입니다.")
public class UserRankContorller {

    private final UserRankService userRankService;


    @GetMapping("/exp")
    @Operation(summary = "유저의 현재 exp", description = "유저의 현재 exp를 가져오는 api 입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 현재 exp 요청을 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "유저 현재 exp 요청을 실패했습니다.")
    })
    public ResponseEntity<UserRankExpResponseDto> userExp(@RequestParam String username){
        return ResponseEntity.ok(userRankService.makeUserRankExpResponse(username));
    }



    @GetMapping("/userRanking")
    @Operation(summary = "유저의 현재 점수", description = "유저의 현재 점수를 가져오는 api 입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 현재 점수 요청을 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "유저 현재 점수 요청을 실패했습니다.")
    })
    public ResponseEntity<UserRankingResponseDto> userRanking(@RequestParam String username){
        return ResponseEntity.ok(userRankService.makeUserRankResponse(username));
    }

    @GetMapping("/school")
    @Operation(summary = "유저랭크 학교 탭 부분", description = "유저 랭크 페이지의 학교 탭의 정보를 위한 api")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 학교 상황 정보 요청을 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "유저 학교 상황 정보 요청을 실패했습니다.")
    })
    public ResponseEntity<SchoolRankResponseDto> schoolTab(@RequestParam String username){
        return ResponseEntity.ok(userRankService.makeUserRankSchoolStatusByUsername(username));
    }

    @GetMapping("/region")
    @Operation(summary = "유저랭크 지역 탭 부분", description = "유저 랭크 페이지의 지역 탭의 정보를 위한 api")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 지역 탭 정보 요청을 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "유저 지역 탭 정보 요청을 실패했습니다.")
    })
    public ResponseEntity<RegionRankResponseDto> regionTab(@RequestParam String username){
        return ResponseEntity.ok(userRankService.makeUserRankRegionStatusByUsername(username));
    }

    @GetMapping("/lang")
    @Operation(summary = "유저랭크 사용 언어 탭 부분", description = "유저 랭크 페이지의 사용 언어 탭의 정보를 위한 api")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 사용 언어 탭 정보 요청을 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "유저 사용 언어 탭 정보 요청을 실패했습니다.")
    })
    public ResponseEntity<UserRankMajorLanguageResponseDto> majorLanguageTab(@RequestParam String username){
        return ResponseEntity.ok(userRankService.makeUserRankLanguageResponseByUsername(username));
    }


}




