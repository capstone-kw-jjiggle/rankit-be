package gitbal.backend.domain.majorlanguage.application;

import gitbal.backend.domain.majorlanguage.MajorLanguage;
import gitbal.backend.domain.user.User;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@RequiredArgsConstructor
@Slf4j
public class MajorLanguageUpdater {


    private final MajorLanguage updatedLanguage;


    public static MajorLanguageUpdater of(MajorLanguage updatedLanguage) {
        return new MajorLanguageUpdater(updatedLanguage);
    }


    public void updateLanguage(User user) {
        if(Objects.isNull(updatedLanguage)) {
            user.setMajorLanguage(null);
            return;
        }
        user.setMajorLanguage(updatedLanguage.getMajorLanguage());
    }
}
