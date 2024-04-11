package gitbal.backend.domain;

import gitbal.backend.entity.School;
import gitbal.backend.entity.dto.SchoolRankDto;
import gitbal.backend.entity.dto.SchoolRankRaceResponseDto;
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

    public SchoolRankRaceResponseDto toResponseDto(List<School> around, String mySchoolName) {
        List<SchoolRankDto> schoolRankDtos = new ArrayList<>();
        for (int i = 0; i < around.size(); i++) {
            String schoolName = around.get(i).getSchoolName();
            Long schoolScore = around.get(i).getScore();
            schoolRankDtos.add(SchoolRankDto.of(schoolName, schoolScore));
        }
        return SchoolRankRaceResponseDto.of(mySchoolName, schoolRankDtos);
    }

}
