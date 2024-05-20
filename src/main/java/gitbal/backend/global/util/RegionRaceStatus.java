package gitbal.backend.global.util;


import gitbal.backend.domain.dto.RegionRankDto;
import gitbal.backend.domain.userPage.dto.RegionRankRaceResponseDto;
import gitbal.backend.domain.entity.Region;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record RegionRaceStatus(List<Region> aroundUsers) implements RaceStatus<Region> {

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
