package gitbal.backend.domain.school;

import gitbal.backend.domain.user.User;
import gitbal.backend.global.util.JoinUpdater;

public class SchoolJoinUpdater implements JoinUpdater {


    @Override
    public void process(User user) {
        updateSchoolByUser(user);
    }

    private void updateSchoolByUser(User findUser) {
        Long score = findUser.getScore();
        School school = findUser.getSchool();
        school.addScore(score);
    }
}
