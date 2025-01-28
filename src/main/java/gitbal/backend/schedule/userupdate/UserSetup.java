package gitbal.backend.schedule.userupdate;

import gitbal.backend.domain.user.UserService;

import java.util.List;
import java.util.stream.Collectors;


public abstract class UserSetup {


    protected List<String> getAllUsernames(UserService userService){
        List<String> userNames = userService.findAllUserNames();

        return userNames.stream().toList();
    }

}
