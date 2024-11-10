package gitbal.backend.api.auth.controller;

import gitbal.backend.api.auth.dto.JoinRequestDto;
import gitbal.backend.api.auth.service.AuthService;
import gitbal.backend.api.auth.dto.UserInfoDto;
import gitbal.backend.api.auth.service.UserAuthInfoService;
import gitbal.backend.global.exception.NotLoginedException;
import gitbal.backend.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
@Tag(name = "인증 관련 처리 API", description = "인증에 필요한 api입니다.")
public class AuthController {

    private final AuthService authService;
    private final UserAuthInfoService userAuthInfoService;

    @PostMapping("/join")
    @Operation(summary = "회원가입(헤더에 토큰 필요 Authorization: Bearer {토큰 값 넣기})", description = "회원가입을 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회원가입에 성공했습니다."),
        @ApiResponse(responseCode = "401", description = "인증된 유저가 없는 상태로 요청하셨습니다. github 로그인을 진행해주세요."),

    })
    public ResponseEntity<String> login(Authentication authentication,
        @RequestBody JoinRequestDto joinRequestDto, HttpServletResponse response) {

        if(Objects.isNull(authentication))
            throw new NotLoginedException();

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

        authService.join(joinRequestDto, principal);

        response.setHeader("accessToken", principal.getAccessToken());
        return ResponseEntity.ok("회원 가입 성공!");
    }



    @GetMapping("/userInfo")
    @Operation(summary = "유저 정보 조회 (헤더에 토큰 필요 Authorization: Bearer {토큰 값 넣기})", description = "유저 정보 조회를 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 정보를 가져오는데 성공했습니다."),
        @ApiResponse(responseCode = "400", description = "유저 정보를 가져오는데 실패했습니다."),
        @ApiResponse(responseCode = "401", description = "인증된 유저가 없는 상태로 요청하셨습니다. github 로그인을 진행해주세요."),
    })
    public ResponseEntity<UserInfoDto> userInfo(Authentication authentication) {
        return userAuthInfoService.getUserInfoByUserName(authentication);
    }


    @DeleteMapping("/withdraw")
    @Operation(summary = "회원탈퇴(헤더에 토큰 필요 Authorization: Bearer {토큰 값 넣기})", description = "회원탈퇴를 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회원탈퇴에 성공했습니다."),
        @ApiResponse(responseCode = "400", description = "유저 정보를 가져오는데 실패했습니다."),
        @ApiResponse(responseCode = "401", description = "인증된 유저가 없는 상태로 요청하셨습니다. github 로그인을 진행해주세요."),
        @ApiResponse(responseCode = "500", description = "사용자 회원 탈퇴에 실패했습니다.")
    })
    public ResponseEntity<String> withdrawService(Authentication authentication) {
        return ResponseEntity.ok(authService.withDrawUser(authentication));
    }
}
