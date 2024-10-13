package gitbal.backend.domain.majorlanguage;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MajorLanguageDto {

    private final String languageName;
    private final Long languageUsageCount;

    public static MajorLanguageDto of(String languageName, Long languageUsageCount) {
        return new MajorLanguageDto(languageName, languageUsageCount);
    }


    public static MajorLanguageDto of(MajorLanguage majorLanguage) {
        return new MajorLanguageDto(majorLanguage.getMajorLanguage(),
            majorLanguage.getLanguageCount());
    }
}
