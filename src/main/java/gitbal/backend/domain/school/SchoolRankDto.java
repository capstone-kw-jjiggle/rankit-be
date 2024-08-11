package gitbal.backend.domain.school;


import gitbal.backend.api.userPage.dto.SchoolRankResponseDto;
import gitbal.backend.global.util.RaceStatus;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class SchoolRankDto implements RaceStatus<School> {

    private List<School> aroundUsers;

    public static SchoolRankDto of(List<School> aroundUsers) {
        return new SchoolRankDto(aroundUsers);
    }

    @Override
    public void sortAroundEntitys() {
        aroundUsers.sort(Comparator.comparing(School::getScore).reversed());
    }

    @Override
    public void addEntity(School school) {
        aroundUsers.add(school);
    }


}
