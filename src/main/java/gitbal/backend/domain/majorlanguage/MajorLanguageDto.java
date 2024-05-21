package gitbal.backend.domain.majorlanguage;


public record MajorLanguageDto(String languageName, Long languageUsageCount) {

    public static MajorLanguageDto of(String languageName, Long languageUsageCount) {
        return new MajorLanguageDto(languageName, languageUsageCount);
    }


    public static MajorLanguageDto of(MajorLanguage majorLanguage) {
        return new MajorLanguageDto(majorLanguage.getMajorLanguage(),
            majorLanguage.getLanguageCount());
    }

    public static MajorLanguage toEntity(MajorLanguageDto majorLanguageDto) {
        return MajorLanguage.builder()
            .majorLanguage(majorLanguageDto.languageName())
            .languageCount(majorLanguageDto.languageUsageCount())
            .build();
    }
}
