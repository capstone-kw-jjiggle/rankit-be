package gitbal.backend.schedule;

import gitbal.backend.schedule.schoolupdate.schoolRank.SchoolRankUpdater;
import gitbal.backend.schedule.userupdate.majorLanguage.UserLanguagesUpdater;
import gitbal.backend.schedule.userupdate.score.UserScoreUpdater;
import gitbal.backend.schedule.userupdate.userGrade.UserGradeUpdater;
import gitbal.backend.schedule.userupdate.userRank.UserRankUpdater;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
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


    @Async("taskAExecutor")
    @Scheduled(initialDelay = 1, fixedRate = 360, timeUnit = TimeUnit.MINUTES) // fixedRate를 사용하여 일정한 6시간의 주기를 가지는것이 중요!
    public void updateUserScore() {
        userScoreUpdater.update();
        userRankUpdater.update();
        schoolRankUpdater.update();
        userGradeUpdater.update();
        log.info("taskA- {} - {}", LocalDateTime.now(), Thread.currentThread().getName());
    }

    @Async("taskBExecutor")
    @Scheduled(initialDelay = 1, fixedRate = 360, timeUnit = TimeUnit.MINUTES) // fixedRate를 사용하여 일정한 6시간의 주기를 가지는것이 중요!
    public void updateUserLanguages() {
        userLanguagesUpdater.update();
        log.info("taskBUpdateUserLanguages - {} - {}", LocalDateTime.now(), Thread.currentThread().getName());
    }







}
