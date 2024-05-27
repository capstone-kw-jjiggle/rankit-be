package gitbal.backend.api.auth.dto;


import gitbal.backend.domain.user.User;

public class UserInfoDto {

    private final String userName;
    private final String univName;
    private final String regionName;
    private final String imagName;
    private final String title;
    private final Integer rank;

    public UserInfoDto(String userName, String univName, String regionName, String imagName,
        String title, Integer rank) {
        this.userName = userName;
        this.univName = univName;
        this.regionName = regionName;
        this.imagName = imagName;
        this.title = title;
        this.rank = rank;
    }

    public static UserInfoDto of(User user) {
        return new UserInfoDto(user.getNickname(), user.getSchool().getSchoolName(),
            user.getRegion().getRegionName(), user.getProfile_img(), null, user.getUserRank());
    }
}
