package gitbal.backend.schedule;

import gitbal.backend.domain.onedaycommit.OneDayCommitService;
import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.region.RegionService;
import gitbal.backend.domain.school.School;
import gitbal.backend.domain.school.SchoolService;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

    private final UserService userService;
    private final RegionService regionService;
    private final SchoolService schoolService;
    private final OneDayCommitService oneDayCommitService;

    @Transactional
    public void schedulingUserScore(List<String> allUserNames) {
        log.info("[schedulingUserScore] method start");
        for (String username : allUserNames) {
            Long newScore = userService.calculateUserScore(username);
            User findUser = userService.findByUserName(username);
            Long oldScore = findUser.getScore();
            School school = findUser.getSchool();
            Region region = findUser.getRegion();
            schoolService.updateSchool(school, username, oldScore, newScore);
            regionService.updateRegion(region, username, oldScore, newScore);
            userService.updateUserScore(findUser, newScore);
        }
        log.info("[schedulingUserScore] method finish");
    }


    @Transactional
    public void schedulingUserCommit(List<String> allUserNames) {
        log.info("[schedulingUserCommit] method start");
        for (String username : allUserNames) {
            Boolean isOneDayCommit = userService.checkUserRecentCommit(username);
            User findUser = userService.findByUserName(username);
            oneDayCommitService.updateCommit(findUser.getOneDayCommit(), isOneDayCommit);
        }
        log.info("[schedulingUserCommit] method finish");
    }
}
