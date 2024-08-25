package gitbal.backend.api.regionPage.controller;

import gitbal.backend.api.regionPage.dto.RegionListPageResponseDto;
import gitbal.backend.api.regionPage.dto.RegionListDto;
import gitbal.backend.api.regionPage.dto.MyRegionInfoResponseDto;
import gitbal.backend.api.regionPage.dto.UserPageListByRegionResponseDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/regionRank")
@RequiredArgsConstructor
@Tag(name = "지역 랭킹 API(구현중)", description = "지역 랭킹 페이지를 위한 api입니다.")
public class RegionRankController {

    private final RegionRankService regionRankService;



    // 애매
    @GetMapping("/myRegion")
    @Operation(summary = "일부 구현 완료 -> 이후 fe 개발자 api 테스트 이후 케이스 따라 추가 수정 진행", description = "내 지역 정보를 나타내는 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "내 지역 정보 요청을 성공했습니다."),
        @ApiResponse(responseCode = "4xx", description = "내 지역 정보 요청을 실패했습니다.")
    })
    public ResponseEntity<MyRegionInfoResponseDto> myRegion(Authentication authentication) {
        return ResponseEntity.ok(regionRankService.getMyRegionInfo(authentication));
    }


    @GetMapping("/regionList")
    @Operation(summary = "지역 리스트 (8.17 수정-개발완료)", description = "지역 리스트를 나타내는 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "지역 리스트 요청을 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "지역 리스트 요청을 실패했습니다.")
    })
    public ResponseEntity<RegionListPageResponseDto<RegionListDto>> regionList() {
        return regionRankService.getRegionList();
    }




    @GetMapping("/userList") // 이름, 점수 , 등수는(list 순서대로 pageable 처리함)
    @Operation(summary = "지역별 유저 리스트 (8.25 개발완료)", description = "지역 리스트에 지역 클릭 후 유저들의 랭킹 리스트 api 요청입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "지역별 유저 리스트 조회를 성공했습니다."),
        @ApiResponse(responseCode = "5xx", description = "지역별 유저 리스트 조회를 실패했습니다.")
    })
    public ResponseEntity<UserPageListByRegionResponseDto> userListBySchoolName(@RequestParam(defaultValue = "1", required = false) int page, @RequestParam String regionName) {
        return ResponseEntity.ok(regionRankService.getUserListByRegionName(page, regionName));
    }


}
