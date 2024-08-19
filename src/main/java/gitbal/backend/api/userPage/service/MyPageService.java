package gitbal.backend.api.userPage.service;

import gitbal.backend.api.userPage.dto.FriendSuggestDTO;
import gitbal.backend.domain.majorlanguage.MajorLanguage;
import gitbal.backend.domain.majorlanguage.infra.MajorLanguageJpaEntity;
import gitbal.backend.domain.region.application.RegionService;
import gitbal.backend.domain.school.SchoolService;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserRepository;
import gitbal.backend.domain.user.UserService;
import gitbal.backend.global.constant.Grade;
import gitbal.backend.global.exception.NotLoginedException;
import gitbal.backend.global.security.CustomUserDetails;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyPageService {
  private final UserService userService;
  private final SchoolService schoolService;
  private final RegionService regionService;
  private final UserRepository userRepository;

  private final int NEAR_SCORE_BOUNDARY = 1000;

  //지역이나 학교 수정해도 수정하기 전 후의 지역점수나 학교점수에 유저 점수 직접 더하고 빼지 않음. (어짜피 점수 업데이트 할 때 반영되니깐)
  @Transactional
  public void modifySchoolName(Authentication authentication, String newSchoolName) {
    User user = checkAuthAndGetUser(authentication);
    updateUserSchool(user, newSchoolName);
  }

  @Transactional
  public void modifyRegionName(Authentication authentication, String newRegionName){
    User user = checkAuthAndGetUser(authentication);
    updateUserRegion(user, newRegionName);
  }

  public ArrayList<FriendSuggestDTO> getFriendSuggestionList(Authentication authentication){
    User user = checkAuthAndGetUser(authentication);
    ArrayList <FriendSuggestDTO> friendSuggestionList =
        Objects.requireNonNull(getResultFriends(user))
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toCollection(ArrayList::new));
    Collections.shuffle(friendSuggestionList);
    return friendSuggestionList;
  }

  private List<User> getResultFriends(User user){
    Grade grade = getNextGrade(user.getGrade());
    String lang = user.getMajorLanguage().getMajorLanguage();
    Long score = user.getScore();

    List<User> allUsers = getAllUserExceptCurrentUser(user);

    User purpleUser = getRandomHighestGradeUser(allUsers);
    User sameLangUser = getRandomSameLanguageUser(allUsers, lang);
    User nextGradeUser = getRandomNextLanguageUser(allUsers, grade);
    User nearScoreUser = getRandomNearScoreUser(allUsers, score);

    List<User> result = new ArrayList<>();
    if (purpleUser != null) {
      result.add(purpleUser);
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

  private User getRandomHighestGradeUser(List<User> allUsers){
    List<User> highestGradeUsers = allUsers.stream()
        .filter(u -> u.getGrade() == Grade.PURPLE).toList();
    return checkNullAndReturn(highestGradeUsers, allUsers);
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

  private FriendSuggestDTO convertToDTO(User user) {
    return FriendSuggestDTO.of(
        user.getNickname(),
        user.getGrade(),
        user.getMajorLanguage().getMajorLanguage(),
        user.getSchool().getSchoolName(),
        user.getRegion().getRegionName(),
        user.getProfile_img()
    );
  }

  private List<User> getAllUserExceptCurrentUser(User user){
    List<User> users = userRepository.findAll();
    users.remove(user);
    return users;
  }


  private User pickRandomUser(List<User> users) {
    Random random = new Random();
    return users.get(random.nextInt(users.size()));
  }


  private Grade getNextGrade(Grade grade) {
    switch (grade) {
      case YELLOW -> {
        return Grade.GREEN;
      }
      case GREEN -> {
        return Grade.BLUE;
      }
      case BLUE -> {
        return Grade.RED;
      }
      case RED -> {
        return Grade.GREY;
      }
      case GREY, PURPLE -> {
        return Grade.PURPLE;
      }
      default -> throw new IllegalArgumentException("알 수 없는 등급입니다: " + grade);
    }
  }

  private User checkAuthAndGetUser(Authentication authentication) {
    if (authentication == null) {
      throw new NotLoginedException();
    }
    CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
    String userName = principal.getNickname();
    return userService.findByUserName(userName);
  }

  private void updateUserSchool(User user, String schoolName) {
    userService.updateUserSchool(user, schoolService.findBySchoolName(schoolName));
  }

  private void updateUserRegion(User user, String regionName) {
    userService.updateUserRegion(user, regionService.findByRegionName(regionName));
  }
}
