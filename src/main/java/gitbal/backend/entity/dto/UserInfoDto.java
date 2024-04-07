package gitbal.backend.entity.dto;

public record UserInfoDto2(String userName, String univName, String regionName, String imgName ,String userTitle) {

  public static UserInfoDto2 of (String userName, String univName, String regionName, String imgName ,String userTitle){
    return new UserInfoDto2(userName, univName, regionName, imgName, userTitle);
  }
}
