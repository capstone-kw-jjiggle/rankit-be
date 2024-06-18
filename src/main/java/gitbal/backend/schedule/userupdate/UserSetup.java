package gitbal.backend.schedule.userupdate;

import gitbal.backend.domain.user.UserService;
import java.util.List;


public abstract class UserSetup {

    protected List<String> getAllUsernames(UserService userService){
        //List<String> allUserNames = userService.findAllUserNames();
        // TODO : 임시로 진행하여 임의의 github id 로 등록 진행
        List<String> allUserNames = List.of(
            userService.findByUserName("leesj000603").getNickname()
        );
        return allUserNames;
    }

}
