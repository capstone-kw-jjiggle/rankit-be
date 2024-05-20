package gitbal.backend.domain.userPage.dto;


import gitbal.backend.global.dto.RegionRankDto;
import java.util.List;
import lombok.Getter;

//TODO : 제거하긴 했지만 이 클래스가 있어야 하는지에 대한 의문이 있음 고민해보기!

@Getter
public class RegionRankRaceResponseDto {

    private List<RegionRankDto> regionRankDtoList;

    public RegionRankRaceResponseDto(List<RegionRankDto> regionRankDtoList) {
        this.regionRankDtoList = regionRankDtoList;
    }

    public static RegionRankRaceResponseDto of(List<RegionRankDto> regionRankDtoList) {
        return new RegionRankRaceResponseDto(regionRankDtoList);
    }
}
