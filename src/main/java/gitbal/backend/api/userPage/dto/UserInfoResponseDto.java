package gitbal.backend.api.userPage.dto;

import gitbal.backend.global.constant.Grade;

public record UserInfoResponseDto(String username, Grade grade, String profileImg) {

    public static UserInfoResponseDto of(String username, Grade grade, String profileImg) {
        return new UserInfoResponseDto(username, grade, profileImg);
    }

}
