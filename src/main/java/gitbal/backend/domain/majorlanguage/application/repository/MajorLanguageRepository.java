package gitbal.backend.domain.majorlanguage.application.repository;

import gitbal.backend.domain.majorlanguage.infra.MajorLanguageJpaEntity;
import java.util.List;
import java.util.Optional;

public interface MajorLanguageRepository{

    void save(MajorLanguageJpaEntity majorLanguage);

    Optional<MajorLanguageJpaEntity> findById(Long id);

    List<MajorLanguageJpaEntity> findAll();

    void delete(MajorLanguageJpaEntity majorLanguage);


}
