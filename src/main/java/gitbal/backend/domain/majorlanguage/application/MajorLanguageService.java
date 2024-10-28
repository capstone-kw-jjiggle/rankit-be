package gitbal.backend.domain.majorlanguage.application;

import gitbal.backend.api.userPage.dto.UserRankMajorLanguageResponseDto;
import gitbal.backend.domain.majorlanguage.MajorLanguage;
import gitbal.backend.domain.user.User;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MajorLanguageService {


    private final TopLanguageService topLanguageService;

    public MajorLanguage getUserTopLaunguage(String username) {
      return requestFindUserTopLanguage(username);
    }

    private MajorLanguage requestFindUserTopLanguage(String username) {
        ResponseEntity<String> response = topLanguageService.requestTopLanguageQuery(username);
        log.info("response is {}", response.getBody());
        // JSON 응답에서 사용 언어 추출
        MajorLanguageJsonParser majorLanguageJsonParser = new MajorLanguageJsonParser();
        Map<String, Integer> languageCounts = Objects.
            requireNonNull(majorLanguageJsonParser.parse(
                response.getBody()));

        Optional<Entry<String, Integer>> first = languageCounts.entrySet().stream()
            .sorted(Comparator.comparing(Entry<String, Integer>::getValue).reversed())
            .findFirst();


        if (first.isPresent()) {
            Map.Entry<String, Integer> entry = first.get();

            return MajorLanguage.builder()
                .majorLanguage(entry.getKey())
                .languageCount(Long.valueOf(entry.getValue()))
                .build();
        }

        // languageCounts가 비어있다면 null 반환
        return null;
    }


    @Transactional
    public UserRankMajorLanguageResponseDto findMostUsageLanguageByUsername(User findUser) {
        return UserRankMajorLanguageResponseDto.of(findUser.getMajorLanguage());
    }


    @Transactional
    public void updateUserLanguage(MajorLanguage updatedLanguage, User user) {
        MajorLanguageUpdater majorLanguageUpdater = MajorLanguageUpdater.of(updatedLanguage);
        majorLanguageUpdater.updateLanguage(user);
    }

}
