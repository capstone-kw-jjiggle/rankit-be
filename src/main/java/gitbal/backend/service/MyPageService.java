package gitbal.backend.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import gitbal.backend.entity.Region;
import gitbal.backend.entity.School;
import gitbal.backend.entity.User;
import gitbal.backend.exception.NotFoundRegionException;
import gitbal.backend.exception.NotFoundSchoolException;
import gitbal.backend.exception.NotFoundUserException;
import gitbal.backend.exception.NotLoginedException;
import gitbal.backend.repository.RegionRepository;
import gitbal.backend.repository.SchoolRepository;
import gitbal.backend.repository.UserRepository;
import gitbal.backend.security.CustomUserDetails;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {
  private final UserRepository userRepository;
  private final SchoolRepository schoolRepository;
  private final RegionRepository regionRepository;

  //지역이나 학교 수정해도 수정하기 전 후의 지역점수나 학교점수에 유저 점수 직접 더하고 빼지 않음. (어짜피 점수 업데이트 할 때 반영되니깐)

  public ResponseEntity<String> modifySchoolName(Authentication authentication, String newSchoolName) {
    if (authentication == null) {
      throw new NotLoginedException();
    }
    CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
    String username = principal.getNickname();

    User user = userRepository.findByNickname(username).orElseThrow(NotFoundUserException::new);
    School newSchool = schoolRepository.findBySchoolName(newSchoolName).orElseThrow(NotFoundSchoolException::new);

    try{

      user.setSchool(newSchool);
      userRepository.save(user);
      return ResponseEntity.ok("학교가 성공적으로 수정되었습니다.");

    } catch (Exception e) {

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

    }
  }

  public ResponseEntity<String> modifyRegionName(Authentication authentication, String newRegionName){
    if (authentication == null){
      throw new NotLoginedException();
    }
    CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
    String username = principal.getNickname();

    User user = userRepository.findByNickname(username).orElseThrow(NotFoundUserException::new);
    Region newRegion = regionRepository.findByRegionName(newRegionName).orElseThrow(NotFoundRegionException::new);

    try {

      user.setRegion(newRegion);
      userRepository.save(user); // save가 아닌 다른 방식 찾아야함
      return ResponseEntity.ok("지역이 성공적으로 수정되었습니다.");

    } catch (Exception e) {

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

    }
  }

  public ResponseEntity<String> modifyProfileImg (Authentication authentication, String newProfileImgUrl) {
    if (authentication == null){
      throw new NotLoginedException();
    }
    CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
    String username = principal.getNickname();

    User user = userRepository.findByNickname(username).orElseThrow(NotFoundUserException::new);

    try {

      user.setProfileImg(newProfileImgUrl);
      userRepository.save(user);
      return ResponseEntity.ok("이미지가 성공적으로 수정되었습니다.");

    } catch (Exception e) {

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

    }

  }

  public ResponseEntity<String> deleteProfileImg (Authentication authentication) {
    if (authentication == null){
      throw new NotLoginedException();
    }
    CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
    String username = principal.getNickname();

    User user = userRepository.findByNickname(username).orElseThrow(NotFoundUserException::new);

    try {

      user.setProfileImg(null);
      userRepository.save(user);
      return ResponseEntity.ok("이미지가 성공적으로 삭제되었습니다.");

    } catch (Exception e) {

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

    }

  }

  public ResponseEntity<String> withDrawUser (Authentication authentication) {
    if (authentication == null){
      throw new NotLoginedException();
    }
    CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
    String username = principal.getNickname();

    User user = userRepository.findByNickname(username).orElseThrow(NotFoundUserException::new);
    try {
      userRepository.delete(user);
      return ResponseEntity.ok("성공적으로 탈퇴 처리 되었습니다.");

    } catch (Exception e){

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

    }

  }
}
