package gitbal.backend.entity.dto;

import gitbal.backend.entity.Grade;

public record MainPageUserDto(String username, long userscore, Grade grade) {

}
