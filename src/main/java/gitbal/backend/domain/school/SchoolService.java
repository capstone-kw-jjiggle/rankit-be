package gitbal.backend.domain.school;

import gitbal.backend.domain.user.User;
import gitbal.backend.global.exception.NotFoundSchoolException;
import gitbal.backend.global.util.SurroundingRankStatus;
import gitbal.backend.global.exception.JoinException;
import gitbal.backend.domain.user.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchoolService {


  private final int SCHOOL_AROUND_RANGE = 3;
  private final SchoolRepository schoolRepository;
  private final UserRepository userRepository;

  public School findBySchoolName(String schoolName) {
    return schoolRepository.findBySchoolName(schoolName)
        .orElseThrow(NotFoundSchoolException::new);
  }

  public void joinNewUserScore(User findUser) {
    Long score = findUser.getScore();
    School school = findUser.getSchool();
    school.addScore(score);
  }


  public SchoolRaceStatus findSchoolScoreRaced(Long score) {
    int forwardCount = schoolRepository.schoolScoreRacedForward(score);
    int backwardCount = schoolRepository.schoolScoreRacedBackward(score);
    log.info("forwardCount = {} backwardCount = {}", forwardCount, backwardCount);

    SurroundingRankStatus surroundingRankStatus = SurroundingRankStatus.calculateSchoolRegionForwardBackward(
        forwardCount, backwardCount, SCHOOL_AROUND_RANGE);

    log.info("after forwardCount = {} backwardCount = {}", forwardCount, backwardCount);
    return SchoolRaceStatus.of(
        schoolRepository.schoolScoreRaced(score, surroundingRankStatus.getForwardCount(),
            surroundingRankStatus.getBackwardCount()));
  }

  public void insertSchoolTopContributorInfo() {
    List<User> users = userRepository.findAll();
    for (School school : schoolRepository.findAll()) {
      for (User user : users) {
        if (user.getSchool() != null && user.getSchool().getId() == school.getId()) {
          school.updateContributerInfo(user.getNickname(), user.getScore());
          schoolRepository.save(school);
        }
      }
    }

  }
}
