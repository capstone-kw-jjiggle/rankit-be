package gitbal.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mainPage")
@Tag(name = "메인페이지 API(구현중)", description = "메인페이지에 필요한 정보를 위한 api입니다.")
public class MainPageController {

    @GetMapping("/users")
    @Operation(summary = "메인 페이지 유저들에 대한 API입니다.", description = "메인에 나타나는 유저들에 대한 API 12명씩")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저들을 가져오는데 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "유저들을 가져오는데 실패했습니다.")
    })
    public void users(){

    }


    @GetMapping("/firstRank")
    @Operation(summary = "메인 페이지에 나타나는 1등 학교, 지역을 나타내는 API입니다.", description = "메인에 있는 1등을 나타내기 위한 API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "메인 페이지 1등 표시 지역, 학교 표시에 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "메인 페이지 1등 표시 지역, 학교 표시에 실패했습니다.")
    })
    public void firstRank(){

    }

    @PutMapping("/search")
    @Operation(summary = "메인 페이지 검색한 유저를 나오게 하는 API입니다.", description = "메인 검색 유저 관련 api 12명씩 대소문자 구분없이 검색.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 검색에 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "유저 검색에 실패했습니다.")
    })
    public void search(){

    }



}
