package gitbal.backend.schedule;

import gitbal.backend.schedule.schoolupdate.schoolRank.SchoolRankUpdater;
import gitbal.backend.schedule.userupdate.majorLanguage.UserLanguagesUpdater;
import gitbal.backend.schedule.userupdate.score.UserScoreUpdater;
import gitbal.backend.schedule.userupdate.userGrade.UserGradeUpdater;
import gitbal.backend.schedule.userupdate.userRank.UserRankUpdater;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulingService {

    private final UserScoreUpdater userScoreUpdater;
    private final UserLanguagesUpdater userLanguagesUpdater;
    private final SchoolRankUpdater schoolRankUpdater;
    private final UserRankUpdater userRankUpdater;
    private final UserGradeUpdater userGradeUpdater;




    @Scheduled(initialDelay = 3, fixedRate = 360, timeUnit = TimeUnit.MINUTES)
    public void updateUserScore() {
        userScoreUpdater.update();
    }


    @Scheduled(initialDelay = 4, fixedRate = 360, timeUnit = TimeUnit.MINUTES)
    public void updateUserLanguages() {
        userLanguagesUpdater.update();
    }

    @Scheduled(initialDelay = 4, fixedRate = 360, timeUnit = TimeUnit.MINUTES)
    public void updateSchoolRanks() {
        schoolRankUpdater.update();
    }

    @Scheduled(initialDelay = 4, fixedRate = 360, timeUnit = TimeUnit.MINUTES)
    public void updateUserRanks() {
        userRankUpdater.update();
    }


    @Scheduled(initialDelay = 5, fixedRate = 360, timeUnit = TimeUnit.MINUTES)
    public void updateUserGrade() {
        userGradeUpdater.update();
    }

}
