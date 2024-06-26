package gitbal.backend.domain.majorlanguage;

import gitbal.backend.global.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class MajorLanguage extends BaseTimeEntity {

    private Long id;
    private String majorLanguage;
    private Long languageCount;


    public void updateMajorLanguage(MajorLanguage beforeLanguage, MajorLanguage updateLanguage){
        beforeLanguage.setMajorLanguage(updateLanguage.majorLanguage);
        beforeLanguage.setLanguageCount(updateLanguage.languageCount);
    }


    @Override
    public String toString() {
        return "MajorLanguage{" +
            "majorLanguage='" + majorLanguage + '\'' +
            ", languageCount=" + languageCount +
            '}';
    }
}
