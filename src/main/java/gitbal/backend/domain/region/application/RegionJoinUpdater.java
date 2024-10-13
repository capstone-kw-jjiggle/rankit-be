package gitbal.backend.domain.region.application;

import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.user.User;
import gitbal.backend.global.util.JoinUpdater;


public class RegionJoinUpdater implements JoinUpdater{


    @Override
    public void process(User user) {
        updateRegionByUser(user);

    }


    private void updateRegionByUser(User findUser) {
        Long score = findUser.getScore();
        Region region = findUser.getRegion();
        region.addScore(score);
    }



}
