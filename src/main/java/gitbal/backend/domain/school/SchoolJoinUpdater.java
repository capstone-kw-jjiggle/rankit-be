package gitbal.backend.domain.school;

import gitbal.backend.domain.user.User;
import gitbal.backend.global.util.ContributorUpdater;
import gitbal.backend.global.util.JoinUpdater;

public class SchoolJoinUpdater implements JoinUpdater, ContributorUpdater<School> {


    @Override
    public void process(User user) {
        updateSchoolByUser(user);
    }

    @Override
    public void updateContributor(School school, String username, Long newScore) {
        school.updateContributerInfo(username, newScore);
    }

    private void updateSchoolByUser(User findUser) {
        Long score = findUser.getScore();
        School school = findUser.getSchool();
        school.addScore(score);
        updateContributor(school, findUser.getNickname(), findUser.getScore()); // 이 친구 관련 다시 생각해보기 -> region에도 존재함.
    }
}
