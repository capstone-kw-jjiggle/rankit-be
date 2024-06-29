package gitbal.backend.domain.majorlanguage;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class MajorLanguage{

    private Long id;
    private String majorLanguage;
    private Long languageCount;

    public static  Map<String, Integer>extractFiveLanguages(Map<String, Integer> languageCounts) {
        Map<String, Integer> topLanguages = new LinkedHashMap<>();
        languageCounts.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .limit(5)
            .forEach(entry -> topLanguages.put(entry.getKey(), entry.getValue()));
        return topLanguages;
    }


    public void updateMajorLanguage(MajorLanguage updateLanguage){
        this.majorLanguage=updateLanguage.majorLanguage;
        this.languageCount=updateLanguage.languageCount;
    }


    @Override
    public String toString() {
        return "MajorLanguage{" +
            "majorLanguage='" + majorLanguage + '\'' +
            ", languageCount=" + languageCount +
            '}';
    }
}
