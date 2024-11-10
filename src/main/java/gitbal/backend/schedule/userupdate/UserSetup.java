package gitbal.backend.schedule.userupdate;

import gitbal.backend.domain.user.UserService;
import gitbal.backend.global.mock.GenerateRealMockUser;

import java.util.List;
import java.util.stream.Collectors;


public abstract class UserSetup {

    private final GenerateRealMockUser generateRealMockUser = new GenerateRealMockUser();

    protected List<String> getAllUsernames(UserService userService){
        List<String> allUserNamesExcludeMockData = userService.findAllUserNames();

        return allUserNamesExcludeMockData.stream()
                .filter(u -> !generateRealMockUser.getNames().contains(u))
                .collect(Collectors.toList());
    }

}
