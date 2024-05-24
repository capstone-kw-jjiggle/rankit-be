package gitbal.backend.api.userPage.service;

import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.region.RegionService;
import gitbal.backend.domain.school.School;
import gitbal.backend.domain.school.SchoolService;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserRepository;
import gitbal.backend.domain.user.UserService;
import gitbal.backend.global.exception.NotFoundRegionException;
import gitbal.backend.global.exception.NotFoundUserException;
import gitbal.backend.global.exception.NotLoginedException;
import gitbal.backend.domain.region.RegionRepository;
import gitbal.backend.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {
  private final UserService userService;
  private final SchoolService schoolService;
  private final RegionService regionService;
  private final UserRepository userRepository;

  //지역이나 학교 수정해도 수정하기 전 후의 지역점수나 학교점수에 유저 점수 직접 더하고 빼지 않음. (어짜피 점수 업데이트 할 때 반영되니깐)

  public void modifySchoolName(Authentication authentication, String newSchoolName) {
    User user = checkAuthAndGetUser(authentication);
    updateUserSchool(user, newSchoolName);
  }

  public void modifyRegionName(Authentication authentication, String newRegionName){
    User user = checkAuthAndGetUser(authentication);
    updateUserRegion(user, newRegionName);
  }

  public void modifyProfileImg (Authentication authentication, String newProfileImgUrl) {

    User user = checkAuthAndGetUser(authentication);
    userService.updateUserProfileImg(user, newProfileImgUrl);
  }

  public void deleteProfileImg (Authentication authentication) {
    User user = checkAuthAndGetUser(authentication);
    userService.deleteUserProfileImg(user);
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
