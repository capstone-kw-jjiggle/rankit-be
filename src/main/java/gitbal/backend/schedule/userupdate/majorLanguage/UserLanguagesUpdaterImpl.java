package gitbal.backend.schedule.userupdate.majorLanguage;

import gitbal.backend.domain.majorlanguage.MajorLanguage;
import gitbal.backend.domain.majorlanguage.application.MajorLanguageService;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserService;
import gitbal.backend.schedule.userupdate.UserSetup;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserLanguagesUpdaterImpl extends UserSetup implements UserLanguagesUpdater {

    private final UserService userService;
    private final MajorLanguageService majorLanguageService;

    @Override
    public void update() {
        log.info("[languageupdate] method start");
        System.out.println("언어 업데이트 시작");
        List<String> allUsers = super.getAllUsernames(userService);
        for (String username : allUsers) {
            MajorLanguage updateLanguage = majorLanguageService.getUserTopLaunguage(username);
            if(Objects.nonNull(updateLanguage))
                log.info(updateLanguage.toString());
            try {
                majorLanguageService.updateUserLanguage(updateLanguage, userService.findByUserName(username));
            }catch (NullPointerException e){
                User findUser = userService.findByUserName(username);
                saveNewLanguage(updateLanguage, findUser);
            }
        }
        log.info("[languageupdate] method finish");
    }

    private void saveNewLanguage(MajorLanguage updateLanguage, User findUser) {
        findUser.setMajorLanguage(updateLanguage.getMajorLanguage());
    }


}
