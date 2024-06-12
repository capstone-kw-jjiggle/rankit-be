package gitbal.backend.schedule.userupdate.userRank;

import gitbal.backend.domain.user.UserService;
import gitbal.backend.schedule.userupdate.UserSetup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRankUpdaterImpl extends UserSetup implements UserRankUpdater {

  private final UserService userService;


  @Override
  @Transactional
  public void update() {
    userService.updateUserRank();
  }
}
