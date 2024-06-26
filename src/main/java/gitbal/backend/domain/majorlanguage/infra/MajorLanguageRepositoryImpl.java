package gitbal.backend.domain.majorlanguage.infra;

import gitbal.backend.domain.majorlanguage.application.repository.MajorLanguageRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MajorLanguageRepositoryImpl implements MajorLanguageRepository {

    private final MajorLanguageJpaRepository majorLanguageJpaRepository;


    @Override
    public void save(MajorLanguageJpaEntity majorLanguage) {
        majorLanguageJpaRepository.save(majorLanguage);
    }


    @Override
    public Optional<MajorLanguageJpaEntity> findById(Long id) {
        return majorLanguageJpaRepository.findById(id);
    }

    @Override
    public List<MajorLanguageJpaEntity> findAll() {
        return majorLanguageJpaRepository.findAll();
    }

    @Override
    public void delete(MajorLanguageJpaEntity majorLanguage) {
        majorLanguageJpaRepository.delete(majorLanguage);
    }
}
