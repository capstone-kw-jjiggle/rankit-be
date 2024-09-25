package gitbal.backend.api.badge.service;

import gitbal.backend.api.badge.dto.BadgeResponseDTO;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserRepository;
import gitbal.backend.global.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BadgeService {

  private final UserRepository userRepository;
  public BadgeResponseDTO getBadge(String username){
    User user = userRepository.findByNickname(username).orElseThrow(
        NotFoundUserException::new);

    return BadgeResponseDTO.builder()
        .userRank(String.valueOf(user.getUserRank()))
        .score(String.valueOf(user.getScore()))
        .langName(user.getMajorLanguage().getMajorLanguage())
        .grade(String.valueOf(user.getGrade()))
        .build();
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
