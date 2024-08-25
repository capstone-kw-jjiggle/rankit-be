package gitbal.backend.domain.majorlanguage.application.repository;

import gitbal.backend.domain.majorlanguage.MajorLanguageDto;
import gitbal.backend.domain.majorlanguage.infra.MajorLanguageJpaEntity;
import java.util.List;

public interface MajorLanguageRepository{

    void save(MajorLanguageJpaEntity majorLanguage);

    MajorLanguageJpaEntity findById(Long id);

    List<MajorLanguageJpaEntity> findAll();

    void deleteById(Long from);

    void delete(MajorLanguageJpaEntity majorLanguage);

    void updateMajorLanguage(Long id, MajorLanguageDto updateLanguage);
}
