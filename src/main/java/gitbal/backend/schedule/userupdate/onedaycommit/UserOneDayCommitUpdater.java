package gitbal.backend.schedule.userupdate.onedaycommit;

import org.springframework.transaction.annotation.Transactional;

public interface UserOneDayCommitUpdater {

    @Transactional
    void update();

}
