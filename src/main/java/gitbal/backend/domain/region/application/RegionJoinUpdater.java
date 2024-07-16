package gitbal.backend.domain.region.application;

import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.user.User;
import gitbal.backend.global.util.ContributorUpdater;
import gitbal.backend.global.util.JoinUpdater;


public class RegionJoinUpdater implements JoinUpdater, ContributorUpdater<Region> {


    @Override
    public void process(User user) {
        updateRegionByUser(user);

    }


    @Override
    public void updateContributor(Region region, String username, Long newScore) {
        region.updateContributerInfo(username, newScore);
    }

    private void updateRegionByUser(User findUser) {
        Long score = findUser.getScore();
        Region region = findUser.getRegion();
        region.addScore(score);

        updateContributor(region, findUser.getNickname(), findUser.getScore());
    }



}
