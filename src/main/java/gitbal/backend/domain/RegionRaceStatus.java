package gitbal.backend.domain;

import gitbal.backend.entity.Region;
import gitbal.backend.entity.School;
import gitbal.backend.entity.dto.RegionRankDto;
import gitbal.backend.entity.dto.RegionRankRaceResponseDto;
import gitbal.backend.entity.dto.SchoolRankDto;
import gitbal.backend.entity.dto.SchoolRankRaceResponseDto;
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

    public RegionRankRaceResponseDto toResponseDto(List<Region> around, String myRegionName) {
        List<RegionRankDto> regionRankDtos = new ArrayList<>();
        for (int i = 0; i < around.size(); i++) {
            String regionName = around.get(i).getRegionName();
            Long regionScore = around.get(i).getScore();
            regionRankDtos.add(RegionRankDto.of(regionName, regionScore));
        }
        return RegionRankRaceResponseDto.of(myRegionName, regionRankDtos);
    }

}
