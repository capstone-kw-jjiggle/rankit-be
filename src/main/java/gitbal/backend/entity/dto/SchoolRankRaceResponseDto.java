package gitbal.backend.entity.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class SchoolRankRaceResponseDto{

    private String userSchoolName;
    private List<SchoolRankDto> schoolRankDtoList;

    public SchoolRankRaceResponseDto(String userSchoolName, List<SchoolRankDto> schoolRankDtoList) {
        this.userSchoolName = userSchoolName;
        this.schoolRankDtoList = schoolRankDtoList;
    }

    public static SchoolRankRaceResponseDto of(String userSchoolName, List<SchoolRankDto> schoolRankDtoList) {
        return new SchoolRankRaceResponseDto(userSchoolName, schoolRankDtoList);
    }
}
