package gitbal.backend.domain.school;


import gitbal.backend.api.userPage.dto.SchoolRankDto;
import gitbal.backend.api.userPage.dto.SchoolRankRaceResponseDto;
import gitbal.backend.global.util.RaceStatus;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class SchoolRaceStatus implements RaceStatus<School> {

    private List<School> aroundUsers;

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
