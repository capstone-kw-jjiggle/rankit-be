package gitbal.backend.domain.majorlanguage.application;

import gitbal.backend.api.userPage.dto.UserRankMajorLanguageResponseDto;
import gitbal.backend.domain.majorlanguage.MajorLanguage;
import gitbal.backend.domain.majorlanguage.MajorLanguageDto;
import gitbal.backend.domain.majorlanguage.application.repository.MajorLanguageRepository;
import gitbal.backend.domain.majorlanguage.infra.MajorLanguageJpaEntity;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserRepository;
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

    public MajorLanguageJpaEntity getUserTopLaunguage(String username) {
      return requestFindUserTopLanguage(username);
    }

    private MajorLanguageJpaEntity requestFindUserTopLanguage(String username) {
        ResponseEntity<String> response = topLanguageService.requestTopLanguageQuery(username);
        log.info("response is {}", response.getBody());
        // JSON 응답에서 사용 언어 추출
        MajorLanguageJsonParser majorLanguageJsonParser = new MajorLanguageJsonParser();
        Map<String, Integer> languageCounts = Objects.
            requireNonNull(majorLanguageJsonParser.parse(
                response.getBody()));

        if (!languageCounts.isEmpty()) {
            Map.Entry<String, Integer> entry = languageCounts.entrySet().iterator().next();

            return MajorLanguageJpaEntity.builder()
                .majorLanguage(entry.getKey())
                .languageCount(Long.valueOf(entry.getValue()))
                .build();
        }

        // languageCounts가 비어있다면 null 반환
        return null;
    }


    public UserRankMajorLanguageResponseDto findMostUsageLanguageByUsername(User findUser) {
        // TODO : 수정 사항 -> majorLanguage 관련하여서 진행하였을 때 이제는 2개정도밖에 들어가지 않아 논의하여 진행할 수 있도록 진행!
        MajorLanguageDto majorLanguages = MajorLanguageDto.of(
            findUser.getMajorLanguage().toDomain());


        return UserRankMajorLanguageResponseDto.of(majorLanguages.getLanguageName());
    }


    public void updateUserLanguage(MajorLanguage updatedLanguage, Long id) {
        MajorLanguageUpdater majorLanguageUpdater = MajorLanguageUpdater.of(updatedLanguage, majorLanguageRepository);
        majorLanguageUpdater.updateLanguage(id);
    }
}
