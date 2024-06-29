package gitbal.backend.domain.majorlanguage.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MajorLanguageJsonParser {


    public Map<String, Integer> parse(String response) {
        JsonNode repositoriesNode = getRepositoriesNode(response);
        return extractLanguages(repositoriesNode);
    }

    private JsonNode getRepositoriesNode(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response);
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
                languageCounts.put(languageName,
                    languageCounts.getOrDefault(languageName, 0) + size);
            }
        }
        return languageCounts;
    }
}
