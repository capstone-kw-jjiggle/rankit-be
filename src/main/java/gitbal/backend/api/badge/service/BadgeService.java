package gitbal.backend.api.badge.service;

import gitbal.backend.api.badge.dto.BadgeResponseDTO;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserRepository;
import gitbal.backend.global.exception.NotFoundUserException;
import gitbal.backend.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BadgeService {

  private final UserRepository userRepository;
  public BadgeResponseDTO getBadge(CustomUserDetails principal){
    User user = userRepository.findByNickname(principal.getNickname()).orElseThrow(
        NotFoundUserException::new);

    System.out.println("user발견");

    return BadgeResponseDTO.builder()
        .userRank(String.valueOf(user.getUserRank()))
        .score(String.valueOf(user.getScore()))
        .langName(user.getMajorLanguage().getMajorLanguage())
        .grade(user.getGrade())
        .build();
  }
}
