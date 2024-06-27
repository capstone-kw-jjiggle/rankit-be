package gitbal.backend.domain.majorlanguage.application;

import gitbal.backend.domain.majorlanguage.MajorLanguage;
import gitbal.backend.domain.majorlanguage.MajorLanguageDto;
import gitbal.backend.domain.majorlanguage.application.repository.MajorLanguageRepository;
import gitbal.backend.domain.majorlanguage.infra.MajorLanguageJpaEntity;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@RequiredArgsConstructor
@Slf4j
public class MajorLanguageUpdater {

    private final List<MajorLanguage> oldLanguages;
    private final List<MajorLanguage> updatedLanguages;
    private final MajorLanguageRepository majorLanguageRepository;


    public static MajorLanguageUpdater of(List<MajorLanguage> oldLanguages,
        List<MajorLanguage> updatedLanguages,
        MajorLanguageRepository majorLanguageRepository
    ) {
        return new MajorLanguageUpdater(oldLanguages, updatedLanguages, majorLanguageRepository);
    }


    public void updateLanguage() {
        int oldSize = oldLanguages.size();
        int updatedSize = updatedLanguages.size();
        log.info("[updateLanguage] oldSize {}, updatedSize {}", oldSize, updatedSize);
        if (oldSize < updatedSize) {
             updateWhenUpdatedSizeGreaterThanOldSize(oldSize, updatedSize);
             return;
        }
        if (oldSize > updatedSize) {
             updateWhenUpdatedSizeLessThanOldSize(oldSize, updatedSize);
             return;
        }
         updateWhenSameSize(oldSize);
    }

    private void updateWhenSameSize(int oldSize) {
        for (int i = 0; i < oldSize; i++) {
            MajorLanguage beforeLanguage = oldLanguages.get(i);
            beforeLanguage.updateMajorLanguage(updatedLanguages.get(i));
        }
    }

    private void updateWhenUpdatedSizeLessThanOldSize(int oldSize, int updatedSize) {
        for (int i = 0; i < updatedSize; i++) {
            MajorLanguage beforeLanguage = oldLanguages.get(i);
            MajorLanguage updateLanguage = updatedLanguages.get(i);
            majorLanguageRepository.updateMajorLanguage(beforeLanguage.getId(), MajorLanguageDto.of(updateLanguage));
        }
        for (int i = updatedSize; i < oldSize; i++) {
            majorLanguageRepository.deleteById(oldLanguages.get(updatedSize).getId());
            oldLanguages.remove(oldLanguages.get(updatedSize));
        }
    }

    private void updateWhenUpdatedSizeGreaterThanOldSize(int oldSize, int updatedSize) {
        for (int i = 0; i < oldSize; i++) {
            MajorLanguage beforeLanguage = oldLanguages.get(i);
            beforeLanguage.updateMajorLanguage(updatedLanguages.get(i));
        }
        for (int i = oldSize; i < updatedSize; i++) {
            oldLanguages.add(updatedLanguages.get(i));
            majorLanguageRepository.save(MajorLanguageJpaEntity.from(updatedLanguages.get(i)));
        }
    }
}
