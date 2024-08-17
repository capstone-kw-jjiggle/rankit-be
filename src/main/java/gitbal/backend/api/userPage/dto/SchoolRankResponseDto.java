package gitbal.backend.api.userPage.dto;

import lombok.Getter;

@Getter
public class SchoolRankResponseDto {

    private SchoolRankDto schoolRankDto;

    public SchoolRankResponseDto(SchoolRankDto schoolRankDto) {
        this.schoolRankDto = schoolRankDto;
    }

    public static SchoolRankResponseDto of(SchoolRankDto schoolRankDto) {
        return new SchoolRankResponseDto(schoolRankDto);
    }
}
