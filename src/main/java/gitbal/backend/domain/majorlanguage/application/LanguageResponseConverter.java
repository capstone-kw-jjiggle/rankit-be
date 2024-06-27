package gitbal.backend.domain.majorlanguage.application;


import gitbal.backend.api.userPage.dto.UserRankMajorLanguageResponseDto;
import gitbal.backend.domain.majorlanguage.MajorLanguageDto;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class LanguageResponseConverter {

    private List<MajorLanguageDto> majorLanguageDtos;

    public static LanguageResponseConverter of(List<MajorLanguageDto> majorLanguageDtos) {
        return new LanguageResponseConverter(majorLanguageDtos);
    }



    public List<UserRankMajorLanguageResponseDto> convert() {
        long sum = majorLanguageDtos.stream().mapToLong(MajorLanguageDto::getLanguageUsageCount).sum();

        return majorLanguageDtos.stream()
            .map(majorLanguageDto -> UserRankMajorLanguageResponseDto.of(
                majorLanguageDto.getLanguageName(),
                Math.round((double) majorLanguageDto.getLanguageUsageCount() / sum *100)/ 100.0
            ))
            .sorted(Comparator.comparingDouble(UserRankMajorLanguageResponseDto::getLangPercent).reversed())
            .collect(Collectors.toList());
    }
}
