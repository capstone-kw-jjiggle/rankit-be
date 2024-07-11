package gitbal.backend.domain.region.application.repository;

import gitbal.backend.domain.region.Region;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface RegionRepository {

    Optional<Region> findByRegionName(String regionName);

    int regionScoreRacedForward(Long regionScore);

    int regionScoreRacedBackward(Long regionScore);


    List<Region> regionScoreRaced(Long regionScore,
         int fowrardCount,  int backwardCount);


    Region firstRankedRegion();

    List<Region> findAll();

    Page<Region> findAll(Pageable pageable);

    List<Region> findAll(Sort sort);

    Region save(Region region);

    Optional<Region> findById(Long id);




}
