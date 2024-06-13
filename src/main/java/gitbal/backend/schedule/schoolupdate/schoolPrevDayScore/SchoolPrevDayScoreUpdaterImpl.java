package gitbal.backend.schedule.schoolupdate.schoolPrevDayScore;

import gitbal.backend.domain.school.School;
import gitbal.backend.domain.school.SchoolService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchoolPrevDayScoreUpdaterImpl implements SchoolPrevDayScoreUpdater{

  private final SchoolService schoolService;
  @Override
  @Transactional
  public void update() {
    updatePrevDayScore(schoolService.getAllSchoolList());
  }

  private void updatePrevDayScore(List<School> schools) {
    log.info("[schedulingSchoolPrevDayScore] method start");
    for (School school: schools) {
      Long score = school.getScore();
      schoolService.updatePrevDayScore(school, score);
    }
    log.info("[schedulingSchoolPrevDayScore] method finish");
  }
}
