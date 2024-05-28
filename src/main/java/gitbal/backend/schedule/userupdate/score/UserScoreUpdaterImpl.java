package gitbal.backend.schedule.userupdate.score;

import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.region.RegionService;
import gitbal.backend.domain.school.School;
import gitbal.backend.domain.school.SchoolService;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserService;
import gitbal.backend.schedule.userupdate.UserSetup;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserScoreUpdaterImpl extends UserSetup implements UserScoreUpdater{

    private final UserService userService;
    private final RegionService regionService;
    private final SchoolService schoolService;

    @Override
    public void update() {
        log.info("[schedulingUserScore] method start");
        List<String> allUserNames = super.getAllUsers(userService);
        updateUserScoresAndRelatedData(allUserNames);
        log.info("[schedulingUserScore] method finish");
    }

    private void updateUserScoresAndRelatedData(List<String> allUserNames) {
        for (String username : allUserNames) {
            User findUser = userService.findByUserName(username);
            Long newScore = userService.calculateUserScore(username);
            Long oldScore = findUser.getScore();
            School school = findUser.getSchool();
            Region region = findUser.getRegion();
            schoolService.updateByUserScore(school, username, oldScore, newScore);
            regionService.updateByUserScore(region, username, oldScore, newScore);
            userService.updateUserScore(findUser, newScore);
        }
    }
}
