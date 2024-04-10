package gitbal.backend.entity.dto;

public record SchoolRankDto(String schoolName, Long schoolScore) {

    public static SchoolRankDto of(String schoolName, Long schoolScore) {
        return new SchoolRankDto(schoolName, schoolScore);
    }
}
