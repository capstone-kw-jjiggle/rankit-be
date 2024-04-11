package gitbal.backend.service;

import gitbal.backend.domain.RegionRaceStatus;
import gitbal.backend.domain.SurroundingRankStatus;
import gitbal.backend.entity.Region;
import gitbal.backend.entity.User;
import gitbal.backend.exception.JoinException;
import gitbal.backend.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;
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
}
