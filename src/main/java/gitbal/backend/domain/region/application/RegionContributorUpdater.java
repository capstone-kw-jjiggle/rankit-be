package gitbal.backend.domain.region.application;

import gitbal.backend.domain.region.Region;

public interface RegionContributorUpdater {

    void updateContributor(Region region, String username, Long newScore);
}
