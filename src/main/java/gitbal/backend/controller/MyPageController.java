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
@RequestMapping("/api/v1/my")
@Tag(name = "마이페이지 API(미구현)", description = "마이페이지에 필요한 정보를 위한 api입니다.")
public class MyPageController {


    @GetMapping("/title/list")
    @Operation(summary = "현재 가지고 있는 칭호", description = "현재 가지고 있는 칭호를 긁어오는 api 입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "칭호를 가져오는데 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "칭호를 가져오는데 실패했습니다.")
    })
    public void myTitles(){

    }


    @PutMapping("/config/school")
    @Operation(summary = "학교 수정", description = "학교 수정을 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "학교 수정에 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "학교 수정에 실패했습니다.")
    })
    public void modifySchool(){

    }

    @PutMapping("/config/region")
    @Operation(summary = "지역 수정", description = "지역 수정을 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "지역 수정에 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "지역 수정에 실패했습니다.")
    })
    public void modifyRegion(){

    }




}
