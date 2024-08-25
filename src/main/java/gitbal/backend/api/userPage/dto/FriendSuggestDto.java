package gitbal.backend.api.userPage.dto;

import gitbal.backend.global.constant.Grade;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FriendSuggestDto {
  private String nickname;
  private Grade grade;
  private String language;
  private String schoolName;
  private String regionName;
  private String imgURL;


  public static FriendSuggestDto of(String nickname, Grade grade, String language, String schoolName, String regionName, String imgURL) {
    return new FriendSuggestDto(nickname, grade, language, schoolName, regionName, imgURL);
  }
}
