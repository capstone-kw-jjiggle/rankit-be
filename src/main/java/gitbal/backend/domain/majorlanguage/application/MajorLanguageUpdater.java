package gitbal.backend.domain.majorlanguage.application;

import gitbal.backend.domain.majorlanguage.MajorLanguage;
import gitbal.backend.domain.majorlanguage.MajorLanguageDto;
import gitbal.backend.domain.majorlanguage.application.repository.MajorLanguageRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@RequiredArgsConstructor
@Slf4j
public class MajorLanguageUpdater {


    private final MajorLanguage updatedLanguage;
    private final MajorLanguageRepository majorLanguageRepository;


    public static MajorLanguageUpdater of(MajorLanguage updatedLanguage,
        MajorLanguageRepository majorLanguageRepository
    ) {
        return new MajorLanguageUpdater(updatedLanguage, majorLanguageRepository);
    }


    public void updateLanguage(Long id) {
        majorLanguageRepository.updateMajorLanguage(id, MajorLanguageDto.of(updatedLanguage));
    }
}
