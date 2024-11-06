package gitbal.backend.global.mock;

import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.region.application.repository.RegionRepository;
import gitbal.backend.domain.school.School;
import gitbal.backend.domain.school.SchoolRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegionGenerator {

    private final RegionRepository regionRepository;
    private static final int REGION_SIZE = 10;
    private static long id=1;

    private static int count=0;

    public Region generateRegion() {
        Region byId = regionRepository.findById((id % REGION_SIZE) +1).get();
        if(count<5) {
            count++;
        }else{
            count=0;
            id++;
        }
        return byId;
    }
}
