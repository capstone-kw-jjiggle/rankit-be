package gitbal.backend.controller;

import gitbal.backend.entity.dto.MainPageFirstRankResponseDto;
import gitbal.backend.entity.dto.MainPageUserResponseDto;
import gitbal.backend.service.MainPageService;
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
@RequestMapping("/api/v1/mainPage")
@RequiredArgsConstructor
@Tag(name = "메인페이지 API(구현 일부 완료 -> 에러 핸들링, 기획 완료에 따라 진행 예정)", description = "메인페이지에 필요한 정보를 위한 api입니다.")
public class MainPageController {

    private final MainPageService mainPageService;

    @GetMapping("/users")
    @Operation(summary = "메인 페이지 유저들에 대한 API입니다.(일부 구현 완료 -> 등급 기획 완성시 다시 개발 진행)", description = "메인에 나타나는 유저들에 대한 API 12명씩")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저들을 가져오는데 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "유저들을 가져오는데 실패했습니다.")
    })
    public ResponseEntity<MainPageUserResponseDto> users(@RequestParam int page) {
        return ResponseEntity.ok(mainPageService.getUsers(page));
    }


    @GetMapping("/firstRank")
    @Operation(summary = "메인 페이지에 나타나는 1등 학교, 지역을 나타내는 API입니다.(구현 완료)", description = "메인에 있는 1등을 나타내기 위한 API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "메인 페이지 1등 표시 지역, 학교 표시에 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "메인 페이지 1등 표시 지역, 학교 표시에 실패했습니다.")
    })
    public ResponseEntity<MainPageFirstRankResponseDto> firstRank() {
        return ResponseEntity.ok(mainPageService.getMainPageFirstSchoolRegionRank());
    }

    @GetMapping("/search")
    @Operation(summary = "메인 페이지 검색한 유저를 나오게 하는 API입니다.(일부 구현 완료 -> 등급 기획 완성시 다시 개발 진행)", description = "메인 검색 유저 관련 api 12명씩 대소문자 구분없이 검색.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저이름 검색에 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "유저이름 검색에 실패했습니다.")
    })
    public ResponseEntity<MainPageUserResponseDto> search(@RequestParam String searchedname, @RequestParam(required = false, defaultValue = "0") int page) {
        return ResponseEntity.ok(mainPageService.getSearchedUserList(searchedname, page));
    }


}
