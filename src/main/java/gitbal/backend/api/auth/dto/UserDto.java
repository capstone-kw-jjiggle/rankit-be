package gitbal.backend.api.auth.dto;


import gitbal.backend.domain.majorlanguage.infra.MajorLanguageJpaEntity;
import gitbal.backend.domain.onedaycommit.infra.OneDayCommitJpaEntity;
import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.school.School;
import gitbal.backend.domain.user.User;
import java.util.List;

public record UserDto(
    School school,
    Region region,
    OneDayCommitJpaEntity oneDayCommitJpaEntity,
    List<MajorLanguageJpaEntity> majorLanguages,
    String nickname,
    Long score,
    String profile_img) {

    public static UserDto of(School school, Region region, OneDayCommitJpaEntity oneDayCommitJpaEntity,
        List<MajorLanguageJpaEntity> majorLanguages, String nickname, Long score,
        String profile_img) {
        return new UserDto(school, region, oneDayCommitJpaEntity, majorLanguages, nickname, score, profile_img);
    }

    public static User toEntity(UserDto userDto) {
        return User.builder()
            .school(userDto.school())
            .region(userDto.region())
            .oneDayCommitJpaEntity(userDto.oneDayCommitJpaEntity())
            .majorLanguages(userDto.majorLanguages())
            .nickname(userDto.nickname())
            .score(userDto.score())
            .profile_img(userDto.profile_img())
            .build();
    }
}
