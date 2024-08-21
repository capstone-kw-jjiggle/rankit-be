package gitbal.backend.api.userPage.dto;

import gitbal.backend.global.constant.Grade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class FriendSuggestDTO {
  private String nickname;
  private Grade grade;
  private String language;
  private String schoolName;
  private String regionName;
  private String imgURL;


  public static FriendSuggestDTO of(String nickname, Grade grade, String language, String schoolName, String regionName, String imgURL) {
    return new FriendSuggestDTO(nickname, grade, language, schoolName, regionName, imgURL);
  }
}
