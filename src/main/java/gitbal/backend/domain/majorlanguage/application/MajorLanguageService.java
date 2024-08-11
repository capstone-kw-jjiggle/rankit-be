package gitbal.backend.domain.majorlanguage.application;

import gitbal.backend.api.userPage.dto.UserRankMajorLanguageResponseDto;
import gitbal.backend.domain.majorlanguage.MajorLanguage;
import gitbal.backend.domain.majorlanguage.MajorLanguageDto;
import gitbal.backend.domain.majorlanguage.application.repository.MajorLanguageRepository;
import gitbal.backend.domain.majorlanguage.infra.MajorLanguageJpaEntity;
import gitbal.backend.domain.user.User;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MajorLanguageService {

    private final MajorLanguageRepository majorLanguageRepository;

    private final TopLanguageService topLanguageService;

    public List<MajorLanguageJpaEntity> getUserTopLaunguages(String username) {
        return requestFindUserTopLanguage(username).
            entrySet().stream()
            .map(l -> MajorLanguageDto.of(l.getKey(), Long.valueOf(l.getValue())))
            .map(MajorLanguageDto::toEntity).toList();
    }

    private Map<String, Integer> requestFindUserTopLanguage(String username) {
        ResponseEntity<String> response = topLanguageService.requestTopLanguageQuery(username);
        log.info("response is {}", response.getBody());
        // JSON 응답에서 사용 언어 추출
        MajorLanguageJsonParser majorLanguageJsonParser = new MajorLanguageJsonParser();
        Map<String, Integer> languageCounts = Objects.
            requireNonNull(majorLanguageJsonParser.parse(
                response.getBody()));
        // 상위 5개 언어 추출
        return MajorLanguage.extractFiveLanguages(languageCounts);
    }


    public UserRankMajorLanguageResponseDto findMostUsageLanguageByUsername(User findUser) {
        // TODO : 수정 사항 -> majorLanguage 관련하여서 진행하였을 때 이제는 2개정도밖에 들어가지 않아 논의하여 진행할 수 있도록 진행!
        List<MajorLanguageDto> majorLanguages = findUser.getMajorLanguages().stream()
            .map(MajorLanguageJpaEntity::toDomain)
            .map(MajorLanguageDto::of)
            .sorted(Comparator.comparing(MajorLanguageDto::getLanguageUsageCount).reversed())
            .toList();


        return UserRankMajorLanguageResponseDto.of(majorLanguages.get(0).getLanguageName());
    }


    public void updateUserLanguage(List<MajorLanguage> oldLanguages,
        List<MajorLanguage> updatedLanguages) {
        MajorLanguageUpdater majorLanguageUpdater = MajorLanguageUpdater.of(oldLanguages,
            updatedLanguages, majorLanguageRepository);
        majorLanguageUpdater.updateLanguage();

    }
}
