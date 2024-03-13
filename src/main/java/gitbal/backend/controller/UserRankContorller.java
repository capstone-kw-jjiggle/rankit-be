package gitbal.backend.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/userRank")
@Tag(name = "유저 순위 API(미구현)", description = "유저 순위 페이지 api 입니다.")
public class UserRankContorller {


    @GetMapping("/userscore")
    @Operation(summary = "유저의 현재 점수", description = "유저의 현재 점수를 가져오는 api 입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 현재 점수 요청을 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "유저 현재 점수 요청을 실패했습니다.")
    })
    public void userscore(@RequestBody String username){

    }

    @GetMapping("/race")
    @Operation(summary = "유저 경주 현황", description = "유저 경주 상황에 맞게 5명 추출하기 위한 api")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 경주 상황 정보 요청을 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "유저 경주 상황 정보 요청을 실패했습니다.")
    })
    public void raceStatus(@RequestBody String username){

    }

    @GetMapping("/school")
    @Operation(summary = "유저랭크 학교 탭 부분", description = "유저 랭크 페이지의 학교 탭의 정보를 위한 api ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 학교 상황 정보 요청을 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "유저 학교 상황 정보 요청을 실패했습니다.")
    })
    public void schoolTab(@RequestBody String username){

    }

    @GetMapping("/region")
    @Operation(summary = "유저랭크 지역 탭 부분", description = "유저 랭크 페이지의 지역 탭의 정보를 위한 api ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 지역 탭 정보 요청을 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "유저 지역 탭 정보 요청을 실패했습니다.")
    })
    public void regionTab(@RequestBody String username){

    }

    @GetMapping("/lang")
    @Operation(summary = "유저랭크 사용 언어 탭 부분", description = "유저 랭크 페이지의 사용 언어 탭의 정보를 위한 api ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 사용 언어 탭 정보 요청을 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "유저 사용 언어 탭 정보 요청을 실패했습니다.")
    })
    public void majorLanguageTab(@RequestBody String username){

    }






}




