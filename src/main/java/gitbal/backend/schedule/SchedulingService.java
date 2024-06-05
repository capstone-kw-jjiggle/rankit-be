package gitbal.backend.schedule;

import gitbal.backend.schedule.userupdate.majorLanguage.UserLanguagesUpdater;
import gitbal.backend.schedule.userupdate.onedaycommit.UserOneDayCommitUpdater;
import gitbal.backend.schedule.userupdate.score.UserScoreUpdater;
import gitbal.backend.schedule.userupdate.userRank.RankUpdater;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulingService {

    private final UserOneDayCommitUpdater userOneDayCommitUpdater;
    private final UserScoreUpdater userScoreUpdater;
    private final UserLanguagesUpdater userLanguagesUpdater;
    private final RankUpdater rankUpdater;


    @Scheduled(initialDelay = 2, fixedRate = 360, timeUnit = TimeUnit.MINUTES)
    public void updateUserScore(){
       userScoreUpdater.update();
    }
    @Scheduled(cron = "1 0 0 * * ?")
    public void updateUserCommit(){
        userOneDayCommitUpdater.update();
    }

    @Scheduled(initialDelay = 2, fixedRate = 360, timeUnit = TimeUnit.MINUTES)
    public void updateUserLanguages(){userLanguagesUpdater.update();}

    @Scheduled(initialDelay = 3, fixedRate = 360, timeUnit = TimeUnit.MINUTES)
    public void updateRanks(){rankUpdater.update();}
}
