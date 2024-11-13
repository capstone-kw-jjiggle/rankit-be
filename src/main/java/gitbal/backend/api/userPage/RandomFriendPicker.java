package gitbal.backend.api.userPage;

import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserService;
import gitbal.backend.global.constant.Grade;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
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
  private final Random random = new Random();

  public List<User> getFriendList(User currentUser) {
    List<User> allUsers = userService.getAllUserExceptCurrentUser(currentUser);
    Set<User> uniqueFriends = new LinkedHashSet<>();

    // 가장 우선적으로 카테고리당 1명씩 채우는게 목표
    addUniqueUserToSet(uniqueFriends, () -> getRandomHigestGradeUser(allUsers));
    addUniqueUserToSet(uniqueFriends, () -> getSameLangUser(currentUser, allUsers));
    addUniqueUserToSet(uniqueFriends, () -> getRandomNextLanguageUser(allUsers, userService.getNextGrade(currentUser)));
    addUniqueUserToSet(uniqueFriends, () -> getRandomNearScoreUser(allUsers, currentUser.getScore()));

    // 1명씩 했을때 조건에 맞는 유저가 없어 4명보다 적은 경우 랜덤
    while (uniqueFriends.size() < NUM_OF_SUGGESTING_FRIENDS) {
      addUniqueUserToSet(uniqueFriends, () -> pickRandomUser(allUsers));
    }

    return new ArrayList<>(uniqueFriends);
  }

  private void addUniqueUserToSet(Set<User> uniqueFriends, Supplier<User> userSupplier) {
    if (uniqueFriends.size() < NUM_OF_SUGGESTING_FRIENDS) {
      User user = userSupplier.get();
      if (user != null) {
        uniqueFriends.add(user);  // Set이므로 자동으로 중복 처리됨
      }
    }
  }

  public List<User> getAllRandomFriendList() {
    List<User> allUsers = userService.getAllUser();
    Set<User> uniqueRandomFriends = new LinkedHashSet<>();

    while (uniqueRandomFriends.size() < NUM_OF_SUGGESTING_FRIENDS) {
      uniqueRandomFriends.add(pickRandomUser(allUsers));
    }

    return new ArrayList<>(uniqueRandomFriends);
  }

  private User getSameLangUser(User user, List<User> allUsers) {
    if (Objects.nonNull(user.getMajorLanguage())) {
      return getRandomSameLanguageUser(allUsers, user.getMajorLanguage());
    }
    return getRandomLanguageUser(allUsers);
  }

  private User getRandomHigestGradeUser(List<User> allUsers) {
    List<User> highestGradeUsers = allUsers.stream()
        .filter(u -> u.getGrade() == Grade.PURPLE)
        .toList();
    return checkNullAndReturn(highestGradeUsers, allUsers);
  }

  private User getRandomLanguageUser(List<User> allUsers) {
    return pickRandomUser(allUsers);
  }

  private User getRandomSameLanguageUser(List<User> allUsers, String userLang) {
    List<User> sameLangUsers = allUsers.stream()
        .filter(u -> Objects.nonNull(u.getMajorLanguage()))
        .filter(u -> u.getMajorLanguage().equals(userLang))
        .toList();
    return checkNullAndReturn(sameLangUsers, allUsers);
  }

  private User getRandomNextLanguageUser(List<User> allUsers, Grade nextGrade) {
    List<User> higherGradeUsers = allUsers.stream()
        .filter(u -> u.getGrade().equals(nextGrade))
        .toList();
    return checkNullAndReturn(higherGradeUsers, allUsers);
  }

  private User getRandomNearScoreUser(List<User> allUsers, Long userScore) {
    List<User> nearScoreUsers = allUsers.stream()
        .filter(u -> u.getScore() >= userScore - NEAR_SCORE_BOUNDARY
            && u.getScore() <= userScore + NEAR_SCORE_BOUNDARY)
        .toList();
    return checkNullAndReturn(nearScoreUsers, allUsers);
  }

  private User checkNullAndReturn(List<User> list, List<User> allUsers) {
    if (!list.isEmpty()) {
      return pickRandomUser(list);
    }
    return pickRandomUser(allUsers);
  }

  private User pickRandomUser(List<User> users) {
    return users.get(random.nextInt(users.size()));
  }
}
