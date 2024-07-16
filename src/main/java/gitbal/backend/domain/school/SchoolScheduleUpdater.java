package gitbal.backend.domain.school;

import gitbal.backend.global.util.ContributorUpdater;
import gitbal.backend.global.util.ScheduleUpdater;
import org.springframework.stereotype.Service;

@Service
public class SchoolScheduleUpdater implements ScheduleUpdater<School>, ContributorUpdater<School> {

    @Override
    public void update(School school, String username, Long oldScore, Long newScore) {
        school.updateScore(oldScore,newScore);
        school.updateContributerInfo(username, newScore);
    }

    @Override
    public void updateContributor(School school, String username, Long newScore) {
            school.updateContributerInfo(username, newScore);
    }
}
