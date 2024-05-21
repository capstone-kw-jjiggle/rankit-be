package gitbal.backend.domain.school;


import gitbal.backend.domain.school.School;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SchoolRepository extends JpaRepository<School, Long> {

  Optional<School> findBySchoolName(String schoolName);


  @Query("SELECT count(s) FROM School s WHERE s.score > :schoolScore")
  int schoolScoreRacedForward(@Param("schoolScore") Long schoolScore);

  @Query("SELECT count(s) FROM School s WHERE s.score < :schoolScore")
  int schoolScoreRacedBackward(@Param("schoolScore") Long schoolkScore);

  @Query(value =
      "(SELECT * FROM school WHERE score < :schoolScore ORDER BY score DESC LIMIT :behind)" +
          " UNION ALL " +
          "(SELECT * FROM school WHERE score > :schoolScore ORDER BY score ASC LIMIT :front)",
      nativeQuery = true)
  List<School> schoolScoreRaced(@Param("schoolScore") Long schoolScore,
      @Param("front") int fowrardCount, @Param("behind") int backwardCount);

  @Query("SELECT s FROM School s ORDER BY s.score DESC LIMIT 1")
  School firstRankedSchool();

  Page<School> findBySchoolNameContainingIgnoreCase(String searchedSchoolName, Pageable pageable);


  @Query(value = "SELECT RANK() OVER (ORDER BY score DESC) AS `rank` FROM school WHERE school_name = :schoolName", nativeQuery = true)
  int getSchoolRanking(String schoolName);
}
