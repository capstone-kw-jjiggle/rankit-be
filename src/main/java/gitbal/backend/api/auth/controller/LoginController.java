package gitbal.backend.api.auth.controller;


import gitbal.backend.api.auth.service.LoginService;
import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
@Tag(name = "로그인 프로세스 관련 API", description = "로그인 프로세스에 필요한 api입니다.")
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/success")
    @Operation(summary = "로그인", description = "로그인을 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "로그인에 성공했습니다."),
        @ApiResponse(responseCode = "400", description = "유저 정보를 가져오는데 실패했습니다."),
    })
    public String successLogin(@RequestParam String username) throws IOException {
        return loginService.madeRedirectUrl(username);
    }


}
