package gitbal.backend.global.util;


import gitbal.backend.global.dto.SchoolRankDto;
import gitbal.backend.global.entity.School;
import gitbal.backend.domain.userPage.dto.SchoolRankRaceResponseDto;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record SchoolRaceStatus(List<School> aroundUsers) implements RaceStatus<School> {

    public static SchoolRaceStatus of(List<School> aroundUsers) {
        return new SchoolRaceStatus(aroundUsers);
    }

    @Override
    public void sortAroundEntitys() {
        aroundUsers.sort(Comparator.comparing(School::getScore).reversed());
    }

    @Override
    public void addEntity(School school) {
        aroundUsers.add(school);
    }

    public SchoolRankRaceResponseDto toResponseDto(School userSchool, List<School> around) {
        List<SchoolRankDto> schoolRankDtos = new ArrayList<>();
        for (int i = 0; i < around.size(); i++) {
            schoolRankDtos.add(SchoolRankDto.of(userSchool, around.get(i)));
        }
        return SchoolRankRaceResponseDto.of(schoolRankDtos);
    }

}
