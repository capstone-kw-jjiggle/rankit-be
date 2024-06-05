package gitbal.backend.schedule.userupdate.userRank;

import gitbal.backend.domain.school.School;
import gitbal.backend.domain.school.SchoolService;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserRepository;
import gitbal.backend.domain.user.UserService;
import gitbal.backend.schedule.userupdate.UserSetup;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RankUpdaterImpl extends UserSetup implements RankUpdater {

  private final UserService userService;
  private final SchoolService schoolService;

  @Override
  @Transactional
  public void update() {
    userService.updateUserRank();
    schoolService.updateSchoolRank();
  }
}
