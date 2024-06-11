package gitbal.backend.schedule.schoolupdate.schoolRank;

import gitbal.backend.domain.school.SchoolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchoolRankUpdaterImpl implements SchoolRankUpdater {
  private final SchoolService schoolService;

  public void update() {
    schoolService.updateSchoolRank();
  }
}
