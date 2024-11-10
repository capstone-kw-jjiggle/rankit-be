package gitbal.backend.domain.school;

import gitbal.backend.global.util.ScheduleUpdater;
import org.springframework.stereotype.Service;

@Service
public class SchoolScheduleUpdater implements ScheduleUpdater<School>{

    @Override
    public void update(School school, String username, Long oldScore, Long newScore) {
        school.updateScore(oldScore,newScore);
    }

}
