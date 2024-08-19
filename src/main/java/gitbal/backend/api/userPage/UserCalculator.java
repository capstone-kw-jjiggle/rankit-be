package gitbal.backend.api.userPage;

import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserRepository;
import gitbal.backend.global.constant.Grade;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCalculator {
  private final UserRepository userRepository;
  public List<User> getAllUserExceptCurrentUser(User user) {
    List<User> users = userRepository.findAll();
    users.remove(user);
    return users;
  }

  public Grade getNextGrade(Grade grade) {
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


}
