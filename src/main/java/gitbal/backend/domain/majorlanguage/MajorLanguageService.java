package gitbal.backend.domain.majorlanguage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gitbal.backend.domain.user.User;
import gitbal.backend.api.userPage.dto.UserRankMajorLanguageResponseDto;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

    public List<MajorLanguage> getUserTopLaunguages(String username) {
        return requestFindUserTopLanguage(username).entrySet().stream()
            .map(languageInfo -> MajorLanguageDto.of(languageInfo.getKey(), Long.valueOf(languageInfo.getValue())))
            .map(MajorLanguageDto::toEntity).collect(Collectors.toList());
    }

    public Map<String, Integer> requestFindUserTopLanguage(String username) {
        ResponseEntity<String> response = topLanguageService.requestTopLanguageQuery(username);
        log.info("response is {}", response.getBody());
        // JSON 응답에서 사용 언어 추출
        JsonNode repositoriesNode = getRepositoriesNode(response);
        // 사용자의 모든 레포지토리에서 사용된 언어 추출
        Map<String, Integer> languageCounts = extractLanguages(repositoriesNode);
        // 상위 5개 언어 추출
        Map<String, Integer> topLanguages = new LinkedHashMap<>();
        languageCounts.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .limit(5)
            .forEach(entry -> topLanguages.put(entry.getKey(), entry.getValue()));

        return topLanguages;
    }

    private JsonNode getRepositoriesNode(ResponseEntity<String> response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode dataNode = root.get("data");
            JsonNode userNode = dataNode.get("user");
            return userNode.get("repositories").get("nodes");
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private Map<String, Integer> extractLanguages(JsonNode repositoriesNode) {
        Map<String, Integer> languageCounts = new HashMap<>();
        for (JsonNode repositoryNode : repositoriesNode) {
            JsonNode languagesNode = repositoryNode.get("languages").get("edges");
            for (JsonNode languageNode : languagesNode) {
                String languageName = languageNode.get("node").get("name").asText();
                int size = languageNode.get("size").asInt();
                languageCounts.put(languageName, languageCounts.getOrDefault(languageName, 0) + size);
            }
        }
        return languageCounts;
    }



    public List<UserRankMajorLanguageResponseDto> findLanguagePercentByUser(User findUser) {
        List<MajorLanguage> majorLanguages = findUser.getMajorLanguages();
        List<MajorLanguageDto> convertDtos = majorLanguages.stream().map(MajorLanguageDto::of)
            .collect(Collectors.toList());

        LanguageResponseConverter languageResponseConverter = LanguageResponseConverter.of(
            convertDtos);
        return languageResponseConverter.convert();
    }

   
}
