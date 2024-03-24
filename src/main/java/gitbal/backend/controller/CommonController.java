package gitbal.backend.controller;


import gitbal.backend.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "공통사항 API(미구현)", description = "공통 기능 사항에 대한 api 명세입니다.")
public class CommonController {


    // TODO : 여기 정보들은 회원가입 제외 전부 로그인한 유저 기준으로 작업 진행해야함

    @PostMapping("/join")
    @Operation(summary = "회원가입", description = "회원가입을 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "회원가입에 성공했습니다."),
    })
    public String login(Authentication authentication) {

        return "hello";
    }

    //TODO : 이후 제거 해야하는 메서드 -> 로그인 잘 되는지 확인하기 위한 테스트 api
    @GetMapping("/loginCheck")
    public String joinTest(Authentication authentication, HttpServletRequest request) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        System.out.println(principal.getAttributes());
        //System.out.println("accessToken is  " +  request.getHeader("authorization"));
        //System.out.println("oAuth2User is = " + principal.getAttributes().toString());
        return "hello";
    }

    @GetMapping("/userInfo")
    @Operation(summary = "내 정보 조회", description = "내 정보 조회를 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "내 정보를 가져오는데 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "내 정보를 가져오는데 실패했습니다.")
    })
    public void userInfo() {

    }

    @GetMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃을 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "로그아웃에 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "로그아웃에 실패했습니다.")
    })
    public void logout() {

    }

    @DeleteMapping("/withdraw")
    @Operation(summary = "회원탈퇴", description = "회원탈퇴를 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회원탈퇴에 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "회원탈퇴에 실패했습니다.")
    })
    public void withdrawService() {

    }

    @PutMapping("/profileImg")
    @Operation(summary = "프로필 이미지수정", description = "프로필 이미지 수정을 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "프로필 이미지 수정에 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "프로필 이미지 수정에 실패했습니다.")
    })
    public void modifyImg() {

    }

    @DeleteMapping("/profileImg")
    @Operation(summary = "프로필 이미지 삭제", description = "프로필 이미지 삭제를 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "프로필 이미지 삭제에 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "프로필 이미지 삭제에 실패했습니다.")
    })
    public void deleteImg() {

    }


}
