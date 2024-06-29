package gitbal.backend.domain.majorlanguage.infra;

import gitbal.backend.domain.majorlanguage.MajorLanguage;
import gitbal.backend.domain.majorlanguage.MajorLanguageDto;
import gitbal.backend.domain.majorlanguage.application.repository.MajorLanguageRepository;
import gitbal.backend.domain.user.User;
import jakarta.transaction.Transactional;
import java.util.List;
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
    public MajorLanguageJpaEntity findById(Long id) {
        return majorLanguageJpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("MajorLanguage not found"));
    }

    @Override
    public List<MajorLanguageJpaEntity> findAll() {
        return majorLanguageJpaRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        MajorLanguageJpaEntity majorLanguage = findById(id);
        User user = majorLanguage.getUser();
        user.getMajorLanguages().remove(majorLanguage);
        majorLanguageJpaRepository.delete(majorLanguage);
    }

    @Override
    public void delete(MajorLanguageJpaEntity majorLanguage) {
        User user = majorLanguage.getUser();
        user.getMajorLanguages().remove(majorLanguage);
        majorLanguageJpaRepository.delete(majorLanguage);
    }

    @Override
    public void updateMajorLanguage(Long id, MajorLanguageDto updateLanguage) {
        MajorLanguageJpaEntity majorLanguage = findById(id);
        majorLanguage.updateMajorLanguage(updateLanguage);
    }
}
