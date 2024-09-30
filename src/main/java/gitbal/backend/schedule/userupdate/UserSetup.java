package gitbal.backend.schedule.userupdate;

import gitbal.backend.domain.user.UserService;
import java.util.List;
import java.util.stream.Collectors;


public abstract class UserSetup {

    protected List<String> getAllUsernames(UserService userService){
        List<String> allUserNamesExcludeMockData = userService.findAllUserNames();
        return allUserNamesExcludeMockData.stream()
            .filter(u -> !u.contains("User"))
            .toList();
    }

}
