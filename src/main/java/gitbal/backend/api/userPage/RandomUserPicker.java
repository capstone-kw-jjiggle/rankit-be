package gitbal.backend.api.userPage;

import gitbal.backend.domain.user.User;
import gitbal.backend.global.constant.Grade;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomUserPicker {
  private final int NEAR_SCORE_BOUNDARY = 1000;

  public User getRandomHigestGradeUser(List<User> allusers){
    List<User> highestGradeUsers = allusers.stream()
        .filter(u -> u.getGrade() == Grade.PURPLE).toList();

    return checkNullAndReturn(highestGradeUsers,allusers);
  }

  public User getRandomSameLanguageUser(List<User> allUsers, String userLang){
    List<User> sameLangUsers = allUsers.stream()
        .filter(u -> u.getMajorLanguage().getMajorLanguage().equals(userLang))
        .toList();
    return checkNullAndReturn(sameLangUsers, allUsers);
  }

  public User getRandomNextLanguageUser(List<User> allUsers, Grade nextGrade){
    List<User> higherGradeUsers = allUsers.stream()
        .filter(u -> u.getGrade().equals(nextGrade))
        .toList();
    return checkNullAndReturn(higherGradeUsers, allUsers);
  }

  public User getRandomNearScoreUser(List<User> allUsers, Long userScore){
    List<User> nearScoreUsers = allUsers.stream()
        .filter(u -> u.getScore() >= userScore - NEAR_SCORE_BOUNDARY && u.getScore() <= userScore + NEAR_SCORE_BOUNDARY )
        .toList();
    return checkNullAndReturn(nearScoreUsers, allUsers);
  }

  private User checkNullAndReturn(List<User> list, List<User> allUsers){
    if (!list.isEmpty()){
      return pickRandomUser(list);
    } else {
      return pickRandomUser(allUsers);
    }
  }

  private User pickRandomUser(List<User> users) {
    Random random = new Random();
    return users.get(random.nextInt(users.size()));
  }

}
