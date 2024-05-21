package gitbal.backend.api.userPage.dto;

import java.util.List;
import lombok.Getter;

//TODO : 제거하긴 했지만 이 클래스가 있어야 하는지에 대한 의문이 있음 고민해보기!
@Getter
public class SchoolRankRaceResponseDto {

    private List<SchoolRankDto> schoolRankDtoList;

    public SchoolRankRaceResponseDto(List<SchoolRankDto> schoolRankDtoList) {
        this.schoolRankDtoList = schoolRankDtoList;
    }

    public static SchoolRankRaceResponseDto of(List<SchoolRankDto> schoolRankDtoList) {
        return new SchoolRankRaceResponseDto(schoolRankDtoList);
    }
}
