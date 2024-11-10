package gitbal.backend.api.userPage.dto;

import gitbal.backend.global.constant.Grade;

public record UserPageUserInfoResponseDto(String username, Grade grade, String profileImg) {

    public static UserPageUserInfoResponseDto of(String username, Grade grade, String profileImg) {
        return new UserPageUserInfoResponseDto(username, grade, profileImg);
    }

}
