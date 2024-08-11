package gitbal.backend.api.userPage.controller;



import gitbal.backend.api.userPage.dto.RegionRankRaceResponseDto;
import gitbal.backend.api.userPage.dto.SchoolRankResponseDto;
import gitbal.backend.api.userPage.dto.UserRankMajorLanguageResponseDto;
import gitbal.backend.api.userPage.dto.UserRankingResponseDto;
import gitbal.backend.api.userPage.service.UserRankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


//TODO : 이후에 테스트 코드와 함께 각 로직별 리팩토링 작업 진행해야함!
@RestController
@RequestMapping("/api/v1/userRank")
@RequiredArgsConstructor
@Tag(name = "유저 순위 API(구현 완료)", description = "유저 페이지의 rank탭 관련 api 입니다.")
public class UserRankContorller {

    private final UserRankService userRankService;


    //TODO :  본인 소개 수정


    //TODO : 경험치 바로 되어져있는 부분에 대한 api 보내주기! -> 얼마나 남았는지


    //TODO : 수정작업 : 본인 유저 순위만 나올 수 있게
    @GetMapping("/userRanking")
    @Operation(summary = "유저의 현재 점수(8.11 수정-개발 완료)", description = "유저의 현재 점수를 가져오는 api 입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 현재 점수 요청을 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "유저 현재 점수 요청을 실패했습니다.")
    })
    public ResponseEntity<UserRankingResponseDto> userRanking(@RequestParam String username){
        return ResponseEntity.ok(userRankService.makeUserRankResponse(username));
    }

    //TODO : 수정작업 진행 자신의 학교 몇등이고 어느 학교인지만 나오게
    @GetMapping("/school")
    @Operation(summary = "유저랭크 학교 탭 부분(8.11 수정-개발 완료)", description = "유저 랭크 페이지의 학교 탭의 정보를 위한 api")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 학교 상황 정보 요청을 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "유저 학교 상황 정보 요청을 실패했습니다.")
    })
    public ResponseEntity<SchoolRankResponseDto> schoolTab(@RequestParam String username){
        return ResponseEntity.ok(userRankService.makeUserRankSchoolStatusByUsername(username));
    }

    //TODO : 수정작업 진행 자신의 지역 몇등이고 어느 지역인지만 나오게
    @GetMapping("/region")
    @Operation(summary = "유저랭크 지역 탭 부분(개발 완료)", description = "유저 랭크 페이지의 지역 탭의 정보를 위한 api")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 지역 탭 정보 요청을 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "유저 지역 탭 정보 요청을 실패했습니다.")
    })
    public ResponseEntity<RegionRankRaceResponseDto> regionTab(@RequestParam String username){
        return ResponseEntity.ok(userRankService.makeUserRankRegionStatusByUsername(username));
    }

    //TODO : 수정작업 진행 : 자신이 제인 많이 사용한 언어에 대해서만 가져오기
    @GetMapping("/lang")
    @Operation(summary = "유저랭크 사용 언어 탭 부분(개발 완료)", description = "유저 랭크 페이지의 사용 언어 탭의 정보를 위한 api")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 사용 언어 탭 정보 요청을 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "유저 사용 언어 탭 정보 요청을 실패했습니다.")
    })
    public ResponseEntity<List<UserRankMajorLanguageResponseDto>> majorLanguageTab(@RequestParam String username){
        return ResponseEntity.ok(userRankService.makeUserRankLanguageResponseByUsername(username));
    }


}




