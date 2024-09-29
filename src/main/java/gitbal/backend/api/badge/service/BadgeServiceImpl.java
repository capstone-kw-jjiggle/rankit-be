package gitbal.backend.api.badge.service;

import gitbal.backend.api.badge.dto.BadgeResponseDTO;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserRepository;
import gitbal.backend.global.exception.BadgeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BadgeServiceImpl implements BadgeService{
  private final UserRepository userRepository;
  public BadgeResponseDTO getBadgeResponse(String username){
    try {
      User user = userRepository.findByNickname(username).orElseThrow(
          () -> new BadgeException(HttpStatus.BAD_REQUEST));

      return BadgeResponseDTO.builder()
          .userRank(String.valueOf(user.getUserRank()))
          .score(String.valueOf(user.getScore()))
          .langName(user.getMajorLanguage().getMajorLanguage())
          .grade(String.valueOf(user.getGrade()))
          .build();
    } catch (BadgeException badgeException) {
      throw badgeException;
    } catch (Exception e) {
      throw new BadgeException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public BadgeResponseDTO getBadgeFailureResponse(){

    return BadgeResponseDTO.builder()
        .userRank("load-fail")
        .score("load-fail")
        .langName("load-fail")
        .grade("load-fail")
        .build();
  }
}
