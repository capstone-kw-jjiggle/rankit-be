package gitbal.backend.schedule.userupdate.majorLanguage;

import gitbal.backend.domain.majorlanguage.MajorLanguage;
import gitbal.backend.domain.majorlanguage.MajorLanguageService;
import gitbal.backend.domain.user.UserService;
import gitbal.backend.schedule.userupdate.UserSetup;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserLanguagesUpdaterImpl extends UserSetup implements UserLanguagesUpdater {

    private final UserService userService;
    private final MajorLanguageService majorLanguageService;

    @Override
    @Transactional
    public void update() {
        log.info("[languageupdate] method start");
        List<String> allUsers = super.getAllUsernames(userService);
        for (String username : allUsers) {
            List<MajorLanguage> oldLanguages = userService.findMajorLanguagesByUsername(
                username);
            List<MajorLanguage> updatedLanguages = majorLanguageService.getUserTopLaunguages(
                username);

            log.info(oldLanguages.toString());
            log.info(updatedLanguages.toString());

            majorLanguageService.updateUserLanguage(oldLanguages, updatedLanguages);
        }
        log.info("[languageupdate] method finish");
    }





}
