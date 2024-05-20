package gitbal.backend.global.util;


import gitbal.backend.domain.dto.MajorLanguageDto;
import gitbal.backend.domain.userPage.dto.UserRankMajorLanguageResponseDto;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public record LanguageResponseConverter(List<MajorLanguageDto> majorLanguageDtos) {

    public static LanguageResponseConverter of(List<MajorLanguageDto> majorLanguageDtos) {
        return new LanguageResponseConverter(majorLanguageDtos);
    }



    public List<UserRankMajorLanguageResponseDto> convert() {
        long sum = majorLanguageDtos.stream().mapToLong(MajorLanguageDto::languageUsageCount).sum();

        return majorLanguageDtos.stream()
            .map(majorLanguageDto -> UserRankMajorLanguageResponseDto.of(
                majorLanguageDto.languageName(),
                Math.round((double) majorLanguageDto.languageUsageCount() / sum *100)/ 100.0
            ))
            .sorted(Comparator.comparingDouble(UserRankMajorLanguageResponseDto::getLangPercent).reversed())
            .collect(Collectors.toList());
    }
}
