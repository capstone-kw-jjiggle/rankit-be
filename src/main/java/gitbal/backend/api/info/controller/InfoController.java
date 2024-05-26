package gitbal.backend.api.info.controller;

import gitbal.backend.api.info.service.RegionInfoService;
import gitbal.backend.api.info.service.SchoolInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/info")
@RequiredArgsConstructor
@Tag(name = "회원가입 진행 시 학교, 지역 이름들을 불러오기 위한 API(구현 완료)")
public class InfoController {


    private final RegionInfoService regionInfoService;
    private final SchoolInfoService schoolInfoService;

    @GetMapping("/schoolNames")
    @Operation(summary = "학교 이름들 불러오기 (구현 완료)", description = "학교 이름들을 부르기 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "학교 이름 리스트 전달."),
    })
    public List<String> findAllSchoolName() {
        return schoolInfoService.findAllList();
    }


    @GetMapping("/regionNames")
    @Operation(summary = "지역 이름들 불러오기 (구현 완료)", description = "지역 이름들을 부르기 위한 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "지역 이름 리스트 전달."),
    })
    public List<String> findAllRegionName() {
        return regionInfoService.findAllList();
    }

}
