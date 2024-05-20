package gitbal.backend.global.repository;


import gitbal.backend.global.entity.Region;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegionRepository extends JpaRepository<Region, Long> {

    Optional<Region> findByRegionName(String regionName);


    @Query("SELECT count(r) FROM Region r WHERE r.score > :regionScore")
    int regionScoreRacedForward(@Param("regionScore") Long regionScore);

    @Query("SELECT count(r) FROM Region r WHERE r.score < :regionScore")
    int regionScoreRacedBackward(@Param("regionScore") Long regionScore);

    @Query(value =
        "(SELECT * FROM region WHERE score < :regionScore ORDER BY score DESC LIMIT :behind)" +
            " UNION ALL " +
            "(SELECT * FROM region WHERE score > :regionScore ORDER BY score ASC LIMIT :front)",
        nativeQuery = true)
    List<Region> regionScoreRaced(@Param("regionScore") Long regionScore,
        @Param("front") int fowrardCount, @Param("behind") int backwardCount);

    @Query("SELECT r FROM Region r ORDER BY r.score DESC LIMIT 1")
    Region firstRankedRegion();

    @Query(value = "SELECT RANK() OVER (ORDER BY score DESC) AS `rank` FROM region where region_name = :regionName", nativeQuery = true)
    int getRegionRanking(String regionName);
}
