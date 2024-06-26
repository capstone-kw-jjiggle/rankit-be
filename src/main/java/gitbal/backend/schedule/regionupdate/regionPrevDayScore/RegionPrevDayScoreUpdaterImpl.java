package gitbal.backend.schedule.regionupdate.regionPrevDayScore;

import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.region.RegionService;
import gitbal.backend.domain.school.School;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegionPrevDayScoreUpdaterImpl implements RegionPrevDayScoreUpdater{
  private final RegionService regionService;

  @Override
  @Transactional
  public void update() {
    updatePrevDayScore(regionService.getAllRegionList());
  }

  private void updatePrevDayScore(List<Region> regions) {
    log.info("[schedulingRegionPrevDayScore] method start");
    for (Region region : regions) {
      Long score = region.getScore();
      regionService.updatePrevDayScore(region, score);
    }
    log.info("[schedulingRegionPrevDayScore] method finish");
  }
}
