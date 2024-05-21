package gitbal.backend.api.mainPage.dto;


import gitbal.backend.domain.grade.Grade;

public record MainPageUserDto(String username, long userscore, Grade grade) {

}
