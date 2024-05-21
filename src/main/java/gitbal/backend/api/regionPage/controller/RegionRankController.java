package gitbal.backend.api.regionPage.controller;

import gitbal.backend.api.regionPage.dto.RegionListPageResponseDto;
import gitbal.backend.api.regionPage.dto.RegionListDto;
import gitbal.backend.api.regionPage.dto.FirstRankRegionDto;
import gitbal.backend.api.regionPage.dto.MyRegionInfoResponseDto;
import gitbal.backend.api.regionPage.service.RegionRankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/regionRank")
@RequiredArgsConstructor
@Tag(name = "지역 랭킹 API(구현중)", description = "지역 랭킹 페이지를 위한 api입니다.")
public class RegionRankController {

    private final RegionRankService regionRankService;

    @GetMapping("/myRegion")
    @Operation(summary = "일부 구현 완료 -> 이후 fe 개발자 api 테스트 이후 케이스 따라 추가 수정 진행", description = "내 지역 정보를 나타내는 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "내 지역 정보 요청을 성공했습니다."),
        @ApiResponse(responseCode = "4xx", description = "내 지역 정보 요청을 실패했습니다.")
    })
    public ResponseEntity<MyRegionInfoResponseDto> myRegion(Authentication authentication) {
        return ResponseEntity.ok(regionRankService.getMyRegionInfo(authentication));
    }

    @GetMapping("/firstregion")
    @Operation(summary = "1등 지역 정보 (구현 완료)", description = "1등 지역 정보 탭 관련 api 요청입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "1등 지역 정보 요청을 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "1등 지역 정보 요청을 실패했습니다.")
    })
    public ResponseEntity<FirstRankRegionDto> firstRankRegionInfo() {
        return regionRankService.getFirstRankRegionnfo();
    }


    @GetMapping("/regionList")
    @Operation(summary = "지역 리스트 (구현 완료)", description = "지역 리스트를 나타내는 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "지역 리스트 요청을 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "지역 리스트 요청을 실패했습니다.")
    })
    public ResponseEntity<RegionListPageResponseDto<RegionListDto>> regionList() {
        return regionRankService.getRegionList();
    }


}
