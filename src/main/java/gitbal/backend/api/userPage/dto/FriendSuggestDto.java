package gitbal.backend.api.userPage.dto;

import gitbal.backend.global.constant.Grade;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FriendSuggestDto {
  private String username;
  private Grade grade;
  private String language;
  private String schoolName;
  private String regionName;
  private String profileImg;


  public static FriendSuggestDto of(String username, Grade grade, String language, String schoolName, String regionName, String profileImg) {
    return new FriendSuggestDto(username, grade, language, schoolName, regionName, profileImg);
  }
}
