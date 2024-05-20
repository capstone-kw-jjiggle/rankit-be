package gitbal.backend.domain.mainPage.dto;


import gitbal.backend.global.entity.Grade;

public record MainPageUserDto(String username, long userscore, Grade grade) {

}
