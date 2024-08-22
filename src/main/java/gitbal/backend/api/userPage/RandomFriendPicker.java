package gitbal.backend.api.userPage;

import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserService;
import gitbal.backend.global.constant.Grade;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RandomFriendPicker {
  private final int NEAR_SCORE_BOUNDARY = 1000;
  private final UserService userService;

  public List<User> getFriendList(User user) {
    List <User> allUsers = userService.getAllUserExceptCurrentUser(user);
    User highestGradeUser = getRandomHigestGradeUser(allUsers);
    User sameLangUser = getRandomSameLanguageUser(allUsers, user.getMajorLanguage().getMajorLanguage());
    User nextGradeUser = getRandomNextLanguageUser(allUsers, userService.getNextGrade(user));
    User nearScoreUser = getRandomNearScoreUser(allUsers, user.getScore());

    return getList(highestGradeUser, sameLangUser, nextGradeUser, nearScoreUser);
  }

  private List<User> getList(User highestGradeUser, User sameLangUser, User nextGradeUser,
      User nearScoreUser) {
    List<User> result = new ArrayList<>();
    if (highestGradeUser != null) {
      result.add(highestGradeUser);
    }
    if (sameLangUser != null) {
      result.add(sameLangUser);
    }
    if (nextGradeUser != null) {
      result.add(nextGradeUser);
    }
    if (nearScoreUser != null) {
      result.add(nearScoreUser);
    }

    return result;
  }

  private User getRandomHigestGradeUser(List<User> allUsers){
    List<User> highestGradeUsers = allUsers.stream()
        .filter(u -> u.getGrade() == Grade.PURPLE).toList();

    return checkNullAndReturn(highestGradeUsers,allUsers);
  }

  private User getRandomSameLanguageUser(List<User> allUsers, String userLang){
    List<User> sameLangUsers = allUsers.stream()
        .filter(u -> u.getMajorLanguage().getMajorLanguage().equals(userLang))
        .toList();
    return checkNullAndReturn(sameLangUsers, allUsers);
  }

  private User getRandomNextLanguageUser(List<User> allUsers, Grade nextGrade){
    List<User> higherGradeUsers = allUsers.stream()
        .filter(u -> u.getGrade().equals(nextGrade))
        .toList();
    return checkNullAndReturn(higherGradeUsers, allUsers);
  }

  private User getRandomNearScoreUser(List<User> allUsers, Long userScore){
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