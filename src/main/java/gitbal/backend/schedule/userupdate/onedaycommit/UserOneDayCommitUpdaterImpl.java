package gitbal.backend.schedule.userupdate.onedaycommit;


import gitbal.backend.domain.onedaycommit.OneDayCommitService;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserService;
import gitbal.backend.schedule.userupdate.UserSetup;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserOneDayCommitUpdaterImpl extends UserSetup implements UserOneDayCommitUpdater{

    private final UserService userService;
    private final OneDayCommitService oneDayCommitService;

    @Override
    public void update() {
        log.info("[schedulingUserCommit] method start");
        List<String> allUserNames = getAllUsers(userService);
        updateUserCommitsAndRelatedData(allUserNames);
        log.info("[schedulingUserCommit] method finish");
    }

    private void updateUserCommitsAndRelatedData(List<String> allUserNames) {
        for (String username : allUserNames) {
            Boolean isOneDayCommit = userService.checkUserRecentCommit(username);
            User findUser = userService.findByUserName(username);
            oneDayCommitService.updateCommit(findUser.getOneDayCommit(), isOneDayCommit);
        }
    }
}
