package gitbal.backend.schedule.schoolupdate.schoolGrade;

import gitbal.backend.domain.school.SchoolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchoolGradeUpdaterImpl implements SchoolGradeUpdater {
  private final SchoolService schoolService;

  @Override
  public void update() {
    schoolService.updateSchoolGrade();
  }
}
