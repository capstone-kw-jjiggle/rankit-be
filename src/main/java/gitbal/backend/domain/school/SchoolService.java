package gitbal.backend.domain.school;

import gitbal.backend.domain.user.User;
import gitbal.backend.global.constant.SchoolGrade;
import gitbal.backend.global.exception.NotFoundSchoolException;
import gitbal.backend.global.util.JoinUpdater;
import gitbal.backend.global.util.ScheduleUpdater;
import gitbal.backend.global.util.SurroundingRankStatus;
import gitbal.backend.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchoolService {


  private final int SCHOOL_AROUND_RANGE = 3;
  private final int FIRST_RANK = 1;
  private final ScheduleUpdater<School> schoolScheduleUpdater;
  private final SchoolRepository schoolRepository;
  private final UserRepository userRepository;


  public School findBySchoolName(String schoolName) {
    return schoolRepository.findBySchoolName(schoolName)
        .orElseThrow(NotFoundSchoolException::new);
  }

  public void joinNewUserScore(User findUser) {
    JoinUpdater schoolJoinUpdater = new SchoolJoinUpdater();
    schoolJoinUpdater.process(findUser);
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

  public void updateByUserScore(School school, String username, Long oldScore, Long newScore) {
    schoolScheduleUpdater.update(school, username, oldScore, newScore);
  }



  public void updateSchoolRank() {
    List<School> schools = schoolRepository.findAll(Sort.by("score").descending());
    updateRank(schools);
  }

  public void updateSchoolGrade() { // 주기적으로 라는 말 메서드명에 붙이기
    List<School> schools = schoolRepository.findAll(Sort.by("score").descending());
    updateGrade(schools);
  }

  public void updatePrevDayScore(School school, Long newScore) {
    school.updatePrevDayScore(newScore);
  }

  public void updateSchoolChangedScore(School school, Long newScore) {
    school.updateChangedScore(newScore);
  }

  public List<School> getAllSchoolList() {
    List<School> schools = schoolRepository.findAll();
    return schools;
  }

  private void updateRank(List<School> schools){
    int rank = FIRST_RANK;
    int prevRank = FIRST_RANK;

    for (int i = 0; i < schools.size(); i++) {
      School school = schools.get(i);
      if (i > 0 && !Objects.equals(schools.get(i - 1).getScore(), school.getScore())) {
        prevRank = rank;
      }
      school.setSchoolRank(prevRank);
      rank = prevRank + 1;
    }
    schoolRepository.saveAll(schools);
  }

  private void updateGrade(List<School> schools) {
    int totalSchools = schools.size();
    int purpleCount = (int) Math.floor(totalSchools * 0.05);
    int greyCount = (int) Math.floor(totalSchools * 0.1);
    int redCount = (int) Math.floor(totalSchools * 0.15);
    int blueCount = (int) Math.floor(totalSchools * 0.15);
    int greenCount = (int) Math.floor(totalSchools * 0.2);

    int currentCount = 0;
    SchoolGrade currentGrade = SchoolGrade.PURPLE;

    for (School school : schools) {
      if (currentCount >= purpleCount && currentGrade == SchoolGrade.PURPLE) {
        currentGrade = SchoolGrade.GREY;
        currentCount = 0;
      } else if (currentCount >= greyCount && currentGrade == SchoolGrade.GREY) {
        currentGrade = SchoolGrade.RED;
        currentCount = 0;
      } else if (currentCount >= redCount && currentGrade == SchoolGrade.RED) {
        currentGrade = SchoolGrade.BLUE;
        currentCount = 0;
      } else if (currentCount >= blueCount && currentGrade == SchoolGrade.BLUE) {
        currentGrade = SchoolGrade.GREEN;
        currentCount = 0;
      } else if (currentCount >= greenCount && currentGrade == SchoolGrade.GREEN) {
        currentGrade = SchoolGrade.YELLOW;
        currentCount = 0;
      } else if (school.getScore() == 0) {
        school.setGrade(SchoolGrade.YELLOW);
      }

      school.setGrade(currentGrade);
      currentCount++;

      schoolRepository.save(school);
    }
  }
}
