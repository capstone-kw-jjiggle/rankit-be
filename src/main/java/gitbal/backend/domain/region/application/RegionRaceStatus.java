package gitbal.backend.domain.region.application;


import gitbal.backend.api.userPage.dto.RegionRankDto;
import gitbal.backend.api.userPage.dto.RegionRankRaceResponseDto;
import gitbal.backend.domain.region.Region;
import gitbal.backend.global.util.RaceStatus;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegionRaceStatus implements RaceStatus<Region> {

    private List<Region> aroundUsers;

    public static RegionRaceStatus of(List<Region> aroundUsers) {
        return new RegionRaceStatus(aroundUsers);
    }

    @Override
    public void sortAroundEntitys() {
        aroundUsers.sort(Comparator.comparing(Region::getScore).reversed());
    }

    @Override
    public void addEntity(Region region) {
        aroundUsers.add(region);
    }

    public RegionRankRaceResponseDto toResponseDto(List<Region> around) {
        List<RegionRankDto> regionRankDtos = new ArrayList<>();
        for (int i = 0; i < around.size(); i++) {
            String regionName = around.get(i).getRegionName();
            Long regionScore = around.get(i).getScore();
            regionRankDtos.add(RegionRankDto.of(regionName, regionScore));
        }
        return RegionRankRaceResponseDto.of(regionRankDtos);
    }

}
