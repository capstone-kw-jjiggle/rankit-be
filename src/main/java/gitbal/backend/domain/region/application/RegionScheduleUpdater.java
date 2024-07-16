package gitbal.backend.domain.region.application;

import gitbal.backend.domain.region.Region;
import gitbal.backend.global.util.ContributorUpdater;
import gitbal.backend.global.util.ScheduleUpdater;
import org.springframework.stereotype.Service;

@Service
public class RegionScheduleUpdater implements ScheduleUpdater<Region>, ContributorUpdater<Region> {

    @Override
    public void update(Region region, String username, Long oldScore, Long newScore) {
        region.updateScore(oldScore,newScore);
        updateContributor(region, username, newScore);
    }

    @Override
    public void updateContributor(Region region, String username, Long newScore) {
        region.updateContributerInfo(username, newScore);
    }
}
