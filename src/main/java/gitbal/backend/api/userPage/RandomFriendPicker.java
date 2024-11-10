package gitbal.backend.api.userPage;

import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserService;
import gitbal.backend.global.constant.Grade;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class RandomFriendPicker {
  private final int NEAR_SCORE_BOUNDARY = 1000;
  private final int NUM_OF_SUGGESTING_FRIENDS = 4;
  private final UserService userService;

  public List<User> getFriendList(User user) {
    List <User> allUsers = userService.getAllUserExceptCurrentUser(user);
    User highestGradeUser = getRandomHigestGradeUser(allUsers);
    log.info("before SameLangUser");
    User sameLangUser = getSameLangUser(user, allUsers);
    User nextGradeUser = getRandomNextLanguageUser(allUsers, userService.getNextGrade(user));
    User nearScoreUser = getRandomNearScoreUser(allUsers, user.getScore());

    return getList(highestGradeUser, sameLangUser, nextGradeUser, nearScoreUser);
  }

  private User getSameLangUser(User user, List<User> allUsers) {
    if(Objects.nonNull(user.getMajorLanguage()))
      return getRandomSameLanguageUser(allUsers, user.getMajorLanguage());
    log.info("getEmptyLangUser");
    return getRandomLanguageUser(allUsers);
  }

  public List<User> getAllRandomFriendList() {
    List<User> allUsers = userService.getAllUser();
    List<User> result = new ArrayList<>();
    for (int i = 0; i < NUM_OF_SUGGESTING_FRIENDS; i++){
      result.add(pickRandomUser(allUsers));
    }
    return result;
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

  private User getRandomLanguageUser(List<User> allUsers){
    return pickRandomUser(allUsers);
  }

  private User getRandomSameLanguageUser(List<User> allUsers, String userLang){
    List<User> sameLangUsers = allUsers.stream()
        .filter(u -> Objects.nonNull(u.getMajorLanguage()))
        .filter(u -> u.getMajorLanguage().equals(userLang))
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
