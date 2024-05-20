package gitbal.backend.domain.userPage.controller;

import gitbal.backend.domain.userPage.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



// TODO : 이거 이름이 myPage가 맞을지 고민을 조금 해봐야함.
@RestController
@RequestMapping("/api/v1/my")
@RequiredArgsConstructor
@Tag(name = "마이페이지 API(일부 구현)", description = "마이페이지에 필요한 정보를 위한 api입니다.")
public class MyPageController {

    private final MyPageService myPageService;


    @GetMapping("/title/list")
    @Operation(summary = "현재 가지고 있는 칭호 (칭호 기획 이후 구현 예정)", description = "현재 가지고 있는 칭호를 긁어오는 api 입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "칭호를 가져오는데 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "칭호를 가져오는데 실패했습니다.")
    })
    public void myTitles(){

    }


    @PutMapping("/config/school")
    @Operation(summary = "학교 수정 (구현 완료)", description = "학교 수정을 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "학교 수정에 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "학교 수정에 실패했습니다.")
    })
    public ResponseEntity<String> modifySchool(Authentication authentication, @RequestParam String modifySchoolName){
        return myPageService.modifySchoolName(authentication, modifySchoolName);
    }

    @PutMapping("/config/region")
    @Operation(summary = "지역 수정 (예외 처리 필요)", description = "지역 수정을 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "지역 수정에 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "지역 수정에 실패했습니다.")
    })
    public ResponseEntity<String> modifyRegion(Authentication authentication, @RequestParam String modifyRegionName){
        return myPageService.modifyRegionName(authentication, modifyRegionName);
    }


    @PutMapping("/profileImg")
    @Operation(summary = "프로필 이미지수정 (구현 완료)", description = "프로필 이미지 수정을 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "프로필 이미지 수정에 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "프로필 이미지 수정에 실패했습니다.")
    })
    public ResponseEntity<String> modifyImg(Authentication authentication, @RequestParam String newProfileImgUrl ) {
        return myPageService.modifyProfileImg(authentication, newProfileImgUrl);
    }


    @DeleteMapping("/profileImg")
    @Operation(summary = "프로필 이미지 삭제 (구현 완료)", description = "프로필 이미지 삭제를 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "프로필 이미지 삭제에 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "프로필 이미지 삭제에 실패했습니다.")
    })
    public ResponseEntity<String> deleteImg(Authentication authentication) {
        return myPageService.deleteProfileImg(authentication);
    }
}
