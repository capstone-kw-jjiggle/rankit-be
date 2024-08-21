package gitbal.backend.api.userPage.service;

import gitbal.backend.api.userPage.RandomFriendPicker;
import gitbal.backend.api.userPage.dto.FriendSuggestDTO;
import gitbal.backend.domain.region.application.RegionService;
import gitbal.backend.domain.school.SchoolService;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserService;
import gitbal.backend.global.exception.NotLoginedException;
import gitbal.backend.global.security.CustomUserDetails;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
  private final RandomFriendPicker randomUserPicker;

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
    return randomUserPicker.getFriendList(user);
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
