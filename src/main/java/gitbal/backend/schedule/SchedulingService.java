package gitbal.backend.schedule;

import gitbal.backend.schedule.userupdate.onedaycommit.UserOneDayCommitUpdater;
import gitbal.backend.schedule.userupdate.score.UserScoreUpdater;
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


    //TODO : 이후 스케줄링 시간 변경
    @Scheduled(initialDelay = 10000, fixedRate = 30000)
    public void updateUserScore(){
       userScoreUpdater.update();
    }

    //TODO : 이후 스케줄링 시간 변경
    @Scheduled(initialDelay = 10000, fixedRate = 30000)
    public void updateUserCommit(){
        userOneDayCommitUpdater.update();
    }



}
