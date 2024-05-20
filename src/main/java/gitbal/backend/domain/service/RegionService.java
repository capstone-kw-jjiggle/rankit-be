package gitbal.backend.domain.service;

import gitbal.backend.domain.entity.Region;
import gitbal.backend.domain.entity.User;
import gitbal.backend.global.util.RegionRaceStatus;
import gitbal.backend.global.util.SurroundingRankStatus;
import gitbal.backend.exception.JoinException;
import gitbal.backend.domain.repository.RegionRepository;
import gitbal.backend.domain.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegionService {

  private final RegionRepository regionRepository;
  private final UserRepository userRepository;
  private final int REGION_AROUND_RANGE = 3;


  public Region findByRegionName(String regionName) {
    return regionRepository.findByRegionName(regionName)
        .orElseThrow(() -> new JoinException("회원가입 중 지역이름에 대한 정보를 db에서 읽어오는데 실패했습니다."));
  }

  public void joinNewUserScore(User findUser) {
    Long score = findUser.getScore();
    Region region = findUser.getRegion();
    region.addScore(score);
  }

  public RegionRaceStatus findRegionScoreRaced(Long score) {
    int forwardCount = regionRepository.regionScoreRacedForward(score);
    int backwardCount = regionRepository.regionScoreRacedBackward(score);
    log.info("forwardCount = {} backwardCount = {}", forwardCount, backwardCount);

    SurroundingRankStatus surroundingRankStatus = SurroundingRankStatus.calculateSchoolRegionForwardBackward(
        forwardCount, backwardCount, REGION_AROUND_RANGE);

    log.info("after forwardCount = {} backwardCount = {}", forwardCount, backwardCount);
    return RegionRaceStatus.of(
        regionRepository.regionScoreRaced(score, surroundingRankStatus.forwardCount(),
            surroundingRankStatus.backwardCount()));
  }

  public void insertTopContributorInfo() {
    List<User> users = userRepository.findAll();
    for (Region region : regionRepository.findAll()) {
      for (User user : users) {
        if (user.getRegion() != null && user.getRegion().getRegionId() == region.getRegionId()) {
          region.updateContributerInfo(user.getNickname(), user.getScore());
          regionRepository.save(region);
        }
      }
    }
  }

}
