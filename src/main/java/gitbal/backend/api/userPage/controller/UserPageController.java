package gitbal.backend.api.userPage.controller;

import gitbal.backend.api.userPage.dto.FriendSuggestDto;
import gitbal.backend.api.userPage.dto.IntroductionResponseDto;
import gitbal.backend.api.userPage.dto.IntroductionupdateRequestDto;
import gitbal.backend.api.userPage.dto.ModifySchoolRequestDto;
import gitbal.backend.api.userPage.dto.UserPageUserInfoResponseDto;
import gitbal.backend.api.userPage.service.UserPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1/userPage")
@RequiredArgsConstructor
@Tag(name = "유저페이지 API", description = "유저 페이지에 필요한 정보를 위한 api입니다.")
public class UserPageController {

    private final UserPageService userPageService;


    @PutMapping("/my/config/school")
    @Operation(summary = "학교 수정(헤더에 토큰 필요 Authorization: Bearer {토큰 값 넣기})", description = "학교 수정을 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "학교 수정에 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "학교 수정에 실패했습니다.")
    })
    public ResponseEntity<String> modifySchool(Authentication authentication, @RequestBody ModifySchoolRequestDto modifySchoolRequestDto){
        userPageService.modifySchoolName(authentication, modifySchoolRequestDto);
        return ResponseEntity.ok("학교 수정에 성공했습니다.");
    }

    @PutMapping("/my/config/region")
    @Operation(summary = "지역 수정(헤더에 토큰 필요 Authorization: Bearer {토큰 값 넣기})", description = "지역 수정을 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "지역 수정에 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "지역 수정에 실패했습니다.")
    })
    public ResponseEntity<String> modifyRegion(Authentication authentication, @RequestParam String modifyRegionName){
        userPageService.modifyRegionName(authentication, modifyRegionName);
        return ResponseEntity.ok("지역 수정에 성공했습니다.");
    }

    @GetMapping("/suggest/friend")
    @Operation(summary = "친구 추천(헤더에 토큰 필요 Authorization: Bearer {토큰 값 넣기} 토큰이 없다면 랜덤으로 추천합니다.)", description = "친구 추천을 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "친구 추천 리스트를 가져오기 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "친구 추천 리스트를 가져오기 실패했습니다.")
    })
    public ResponseEntity<List<FriendSuggestDto>> suggestFreinds(Authentication authentication){
        return ResponseEntity.ok(userPageService.getFriendSuggestionList(authentication));
    }

    @GetMapping("/get/introduction")
    @Operation(summary = "user 소개글 가져오기", description = "user 소개글을 가져오기 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "user 소개글을 가져오기 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "user 소개글을 가져오기 실패했습니다.")
    })
    public ResponseEntity<IntroductionResponseDto> getIntroduction(String username){
        return ResponseEntity.ok(userPageService.getIntroduction(username));
    }

    @PutMapping("/my/update/introduction")
    @Operation(summary = "user 소개글 수정하기(헤더에 토큰 필요 Authorization: Bearer {토큰 값 넣기})", description = "user 소개글을 수정하기 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "user 소개글을 수정하기 성공했습니다."),
        @ApiResponse(responseCode = "401", description = "인증된 유저가 없는 상태로 요청하셨습니다. github 로그인을 진행해주세요."),
        @ApiResponse(responseCode = "5xx", description = "user 소개글을 수정하기 실패했습니다.")
    })
    public ResponseEntity<String> updateIntroduction(Authentication authentication, @RequestBody IntroductionupdateRequestDto introductionpdateRequestDto){
        userPageService.updateIntroduction(authentication, introductionpdateRequestDto);
        return ResponseEntity.ok("수정이 완료 되었습니다.");
    }


    @GetMapping("/userInfo")
    @Operation(summary = "유저페이지에 필요한 유저 정보를 불러온다.", description = "유저의 페이지에 필요한 정보를 가져오는 api 입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저페이지에 필요한 유저 개인 정보 로딩을 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "유저페이지에 필요한 유저 개인 정보 로딩을 실패했습니다.")
    })
    public ResponseEntity<UserPageUserInfoResponseDto> userInfo(String username){
        return ResponseEntity.ok(userPageService.makeUserInfoResponse(username));
    }



}
