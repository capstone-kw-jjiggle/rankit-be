package gitbal.backend.schedule.userupdate.userRank;

import gitbal.backend.domain.school.School;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RankUpdaterImpl implements RankUpdater {

  private UserRepository userRepository;

  @Override
  public void update() {
    List<User> users = userRepository.findAll(Sort.by("score").descending());
    int rank = 1;
    int prevRank = 1;
    for (int i = 0; i < users.size(); i++) {
      User user = users.get(i);
      if (i > 0 && users.get(i - 1).getScore() != user.getScore()) {
        prevRank = rank;
      }
      user.setUserRank(prevRank);
      userRepository.save(user);
      rank = prevRank + 1;
    }
  }



  private void scoreRankingCalculate(List< School > schools, int rank) {
    int prevRank = rank;
    for (int i = 0; i < schools.size(); i++) {
      School school = schools.get(i);
      if (i > 0 && schools.get(i - 1).getScore() != school.getScore()) {
        prevRank = rank;
      }
      school.setSchoolRank(prevRank);
      rank = prevRank + 1;
    }
  }
}
