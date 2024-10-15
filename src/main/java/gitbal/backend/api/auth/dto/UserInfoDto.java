package gitbal.backend.api.auth.dto;


import gitbal.backend.domain.user.User;
import gitbal.backend.global.constant.Grade;
import gitbal.backend.domain.user.UserRegionSchoolNameDto;
import lombok.Getter;

@Getter
public class UserInfoDto {

    private final String username;
    private final String univName;
    private final String regionName;
    private final String profileImg;
    private final String title;
    private final Integer userRank;
    private final Grade grade;

    public UserInfoDto(String username, String univName, String regionName, String profileImg,
                       String title, Integer userRank, Grade grade) {
        this.username = username;
        this.univName = univName;
        this.regionName = regionName;
        this.profileImg = profileImg;
        this.title = title;
        this.userRank = userRank;
        this.grade=grade;
    }

    public static UserInfoDto of(User user, UserRegionSchoolNameDto userRegionSchoolNameDto) {
        return new UserInfoDto(user.getNickname(), userRegionSchoolNameDto.getSchoolName(),
            userRegionSchoolNameDto.getRegionName(), user.getProfile_img(), null, user.getUserRank(), user.getGrade());
    }

}
