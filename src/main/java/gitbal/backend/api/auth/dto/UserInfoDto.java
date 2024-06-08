package gitbal.backend.api.auth.dto;


import gitbal.backend.domain.user.User;
import gitbal.backend.global.constant.Grade;
import lombok.Getter;

@Getter
public class UserInfoDto {

    private final String userName;
    private final String univName;
    private final String regionName;
    private final String imgUrl;
    private final String title;
    private final Integer rank;
    private final Grade grade;

    public UserInfoDto(String userName, String univName, String regionName, String imgUrl,
        String title, Integer rank, Grade grade) {
        this.userName = userName;
        this.univName = univName;
        this.regionName = regionName;
        this.imgUrl = imgUrl;
        this.title = title;
        this.rank = rank;
        this.grade=grade;
    }

    public static UserInfoDto of(User user) {
        return new UserInfoDto(user.getNickname(), user.getSchool().getSchoolName(),
            user.getRegion().getRegionName(), user.getProfile_img(), null, user.getUserRank(), user.getGrade());
    }
}
