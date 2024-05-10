package gitbal.backend.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import gitbal.backend.entity.Region;
import gitbal.backend.entity.School;
import gitbal.backend.entity.User;
import gitbal.backend.exception.NotLoginedException;
import gitbal.backend.repository.RegionRepository;
import gitbal.backend.repository.SchoolRepository;
import gitbal.backend.repository.UserRepository;
import gitbal.backend.security.CustomUserDetails;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {
  private final UserRepository userRepository;
  private final SchoolRepository schoolRepository;
  private final RegionRepository regionRepository;

  //지역이나 학교 수정해도 수정하기 전 후의 지역점수나 학교점수에 유저 점수 직접 더하고 빼지 않음. (어짜피 점수 업데이트 할 때 반영되니깐)

  public void modifySchoolName(Authentication authentication, String newSchoolName) {
    if (authentication == null){
      throw new NotLoginedException();
    }
    CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
    String username = principal.getNickname();
    User user = null;
    School newSchool = null;
    try {
      user = userRepository.findByNickname(username).get();
      newSchool = schoolRepository.findBySchoolName(newSchoolName).get();
    } catch (NullPointerException e) {
      // Exception 처리
    }
    user.setSchool(newSchool);
    userRepository.save(user);
  }

  public void modifyRegionName(Authentication authentication, String newRegionName){
    if (authentication == null){
      throw new NotLoginedException();
    }
    CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
    String username = principal.getNickname();

    User user = null;
    Region newRegion = null;
    try {
      user = userRepository.findByNickname(username).get();
      newRegion = regionRepository.findByRegionName(newRegionName).get();
    } catch (NullPointerException e) {
      // Exception 처리
    }
    user.setRegion(newRegion);
    userRepository.save(user); // save가 아닌 다른 방식 찾아야함
  }

  public void modifyProfileImg (Authentication authentication, String newProfileImgUrl) {
    if (authentication == null){
      throw new NotLoginedException();
    }
    CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
    String username = principal.getNickname();

    User user = null;
    try {
      user = userRepository.findByNickname(username).get();
    } catch (NullPointerException e) {
      // Exception 처리
    }
    user.setProfileImg(newProfileImgUrl);
    userRepository.save(user);
  }

  public void deleteProfileImg (Authentication authentication) {
    if (authentication == null){
      throw new NotLoginedException();
    }
    CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
    String username = principal.getNickname();

    User user = null;
    try {
      user = userRepository.findByNickname(username).get();
    } catch (NullPointerException e) {
      // Exception 처리
    }
    user.setProfileImg(null);
    userRepository.save(user);
  }

  public void withDrawUser (Authentication authentication) {
    if (authentication == null){
      throw new NotLoginedException();
    }
    CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
    String username = principal.getNickname();

    Optional<User> user = null;
    user = userRepository.findByNickname(username);
    userRepository.delete(user.get());
  }
}
