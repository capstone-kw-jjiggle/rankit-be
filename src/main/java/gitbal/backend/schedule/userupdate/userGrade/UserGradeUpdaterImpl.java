package gitbal.backend.schedule.userupdate.userGrade;

import gitbal.backend.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserGradeUpdaterImpl implements UserGradeUpdater{

  private final UserService userService;
  @Override
  public void update() {
    log.info("[schedulingUserGrade] method start");
    userService.updateUserGrade();
    log.info("[schedulingUserGrade] method finish");
  }
}
