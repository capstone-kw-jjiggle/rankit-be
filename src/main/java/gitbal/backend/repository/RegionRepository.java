package gitbal.backend.repository;

import gitbal.backend.entity.Region;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
    Optional<Region> findByRegionName(String regionName);

}
