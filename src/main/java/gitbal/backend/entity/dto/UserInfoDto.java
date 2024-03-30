package gitbal.backend.entity.dto;



public record UserInfoDto(Long prCount, Long commitCount){
    public static UserInfoDto of(Long prCount, Long commitCount) {
        return new UserInfoDto(prCount, commitCount);
    }



}
