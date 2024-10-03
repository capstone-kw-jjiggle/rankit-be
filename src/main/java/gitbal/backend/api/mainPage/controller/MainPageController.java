package gitbal.backend.api.mainPage.controller;

import gitbal.backend.api.mainPage.dto.MainPageUserResponseDto;
import gitbal.backend.api.mainPage.service.MainPageService;
import gitbal.backend.global.constant.Grade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mainPage")
@RequiredArgsConstructor
@Tag(name = "메인페이지 API(구현 일부 완료 -> 에러 핸들링, 기획 완료에 따라 진행 예정)", description = "메인페이지에 필요한 정보를 위한 api입니다.")
public class MainPageController {

    private final MainPageService mainPageService;

    @GetMapping("/users")
    @Operation(summary = "메인 페이지 유저들에 대한 API입니다.(일부 구현 완료 -> 등급 기획 완성시 다시 개발 진행)", description = "메인에 나타나는 유저들에 대한 API 12명씩")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저들을 가져오는데 성공했습니다."),
        @ApiResponse(responseCode = "400", description = "유저들을 가져오는데 실패했습니다.")
    })
    public ResponseEntity<MainPageUserResponseDto> users(@RequestParam(required = false, defaultValue = "1") int page,  @RequestParam(required = false) String searchedname) {
        return ResponseEntity.ok(mainPageService.getUsers(page, searchedname));
    }

    @GetMapping("/{grade}/users")
    @Operation(summary = "메인 페이지 유저들에 대한 API입니다.(일부 구현 완료 -> 등급 기획 완성시 다시 개발 진행)", description = "메인에 나타나는 유저들에 대한 API 12명씩")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저들을 가져오는데 성공했습니다."),
        @ApiResponse(responseCode = "400", description = "유저들을 가져오는데 실패했습니다.")
    })
    public ResponseEntity<MainPageUserResponseDto> users(@RequestParam(required = false, defaultValue = "1") int page,  @PathVariable Grade grade) {
        return ResponseEntity.ok(mainPageService.getGradeUsers(page, grade));
    }



}
