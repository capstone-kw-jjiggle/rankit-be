package gitbal.backend.domain.majorlanguage.application;

import gitbal.backend.api.userPage.dto.UserRankMajorLanguageResponseDto;
import gitbal.backend.domain.majorlanguage.MajorLanguage;
import gitbal.backend.domain.user.User;
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

        if (!languageCounts.isEmpty()) {
            Map.Entry<String, Integer> entry = languageCounts.entrySet().iterator().next();

            return MajorLanguage.builder()
                .majorLanguage(entry.getKey())
                .languageCount(Long.valueOf(entry.getValue()))
                .build();
        }

        // languageCounts가 비어있다면 null 반환
        return null;
    }


    public UserRankMajorLanguageResponseDto findMostUsageLanguageByUsername(User findUser) {
        return UserRankMajorLanguageResponseDto.of(findUser.getMajorLanguage());
    }


    public void updateUserLanguage(MajorLanguage updatedLanguage, User user) {
        MajorLanguageUpdater majorLanguageUpdater = MajorLanguageUpdater.of(updatedLanguage);
        majorLanguageUpdater.updateLanguage(user);
    }

}
