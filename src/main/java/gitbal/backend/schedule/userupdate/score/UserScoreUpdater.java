package gitbal.backend.schedule.userupdate.score;

import org.springframework.transaction.annotation.Transactional;

public interface UserScoreUpdater {

    @Transactional
    void update();

}
