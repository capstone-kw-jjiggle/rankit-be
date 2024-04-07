package gitbal.backend.entity.dto;

public record UserInfoDto(String userName, String univName, String regionName, String imgName , String userTitle) {

  public static UserInfoDto of (String userName, String univName, String regionName, String imgName ,String userTitle){
    return new UserInfoDto(userName, univName, regionName, imgName, userTitle);
  }
}
