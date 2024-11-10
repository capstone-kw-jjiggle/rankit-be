package gitbal.backend.schedule;

import gitbal.backend.schedule.checker.UnivCertCodeChecker;
import gitbal.backend.schedule.schoolupdate.schoolRank.SchoolRankUpdater;
import gitbal.backend.schedule.userupdate.majorLanguage.UserLanguagesUpdater;
import gitbal.backend.schedule.userupdate.score.UserScoreUpdater;
import gitbal.backend.schedule.userupdate.userGrade.UserGradeUpdater;
import gitbal.backend.schedule.userupdate.userRank.UserRankUpdater;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulingService {

    private final UserScoreUpdater userScoreUpdater;
    private final UserLanguagesUpdater userLanguagesUpdater;
    private final SchoolRankUpdater schoolRankUpdater;
    private final UserRankUpdater userRankUpdater;
    private final UserGradeUpdater userGradeUpdater;
    private final UnivCertCodeChecker univCertCodeChecker;

    @Scheduled(initialDelay = 1, fixedRate = 360, timeUnit = TimeUnit.MINUTES) // fixedRate를 사용하여 일정한 6시간의 주기를 가지는것이 중요!
    @SchedulerLock(name="updateUser")
    @Transactional
    public void updateUser() {
        userScoreUpdater.update();
        userRankUpdater.update();
        schoolRankUpdater.update();
        userGradeUpdater.update();
        userLanguagesUpdater.update();
    }


    @Scheduled(fixedRate = 2, timeUnit = TimeUnit.MINUTES)
    @SchedulerLock(name="certCodeExpiredChecker")
    @Transactional
    public void checkExpireCertCode(){
        univCertCodeChecker.checkExpireCertCodeAndClear();
    }

}
