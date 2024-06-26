package gitbal.backend.domain.majorlanguage;


import gitbal.backend.domain.majorlanguage.infra.MajorLanguageJpaEntity;

public record MajorLanguageDto(String languageName, Long languageUsageCount) {

    public static MajorLanguageDto of(String languageName, Long languageUsageCount) {
        return new MajorLanguageDto(languageName, languageUsageCount);
    }


    public static MajorLanguageDto of(MajorLanguageJpaEntity majorLanguage) {
        return new MajorLanguageDto(majorLanguage.getMajorLanguage(),
            majorLanguage.getLanguageCount());
    }

    public static MajorLanguageJpaEntity toEntity(MajorLanguageDto majorLanguageDto) {
        return MajorLanguageJpaEntity.builder()
            .majorLanguage(majorLanguageDto.languageName())
            .languageCount(majorLanguageDto.languageUsageCount())
            .build();
    }
}
