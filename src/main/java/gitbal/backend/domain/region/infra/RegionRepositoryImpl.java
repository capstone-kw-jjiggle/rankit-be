package gitbal.backend.domain.region.infra;

import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.region.application.repository.RegionRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RegionRepositoryImpl implements RegionRepository {

    private final RegionJpaRepository regionJpaRepository;

    @Override
    public Optional<Region> findByRegionName(String regionName) {
        return regionJpaRepository.findByRegionName(regionName);
    }

    @Override
    public int regionScoreRacedForward(Long regionScore) {
        return regionJpaRepository.regionScoreRacedForward(regionScore);
    }

    @Override
    public int regionScoreRacedBackward(Long regionScore) {
        return regionJpaRepository.regionScoreRacedBackward(regionScore);
    }

    @Override
    public List<Region> regionScoreRaced(Long regionScore, int fowrardCount, int backwardCount) {
        return regionJpaRepository.regionScoreRaced(regionScore, fowrardCount, backwardCount);
    }

    @Override
    public List<Region> findAll() {
        return regionJpaRepository.findAll();
    }

    @Override
    public Page<Region> findAll(Pageable pageable) {
            return regionJpaRepository.findAll(pageable);
    }

    @Override
    public List<Region> findAll(Sort sort) {
        return regionJpaRepository.findAll(sort);
    }

    @Override
    public Region save(Region region) {
        return regionJpaRepository.save(region);
    }

    @Override
    public Optional<Region> findById(Long id) {
        return regionJpaRepository.findById(id);
    }
}
