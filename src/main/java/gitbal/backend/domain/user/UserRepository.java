package gitbal.backend.domain.user;


import gitbal.backend.global.constant.Grade;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.nickname = :nickname")
    Optional<User> findByNickname(String nickname);

    @Query("select u.profile_img from User u where u.nickname = :nickname")
    Optional<String> findProfileImgByNickname(@Param("nickname") String nickname);


    Page<User> findByNicknameContainingIgnoreCase(String searchedname, Pageable pageable);


    @Query(value = "(SELECT * FROM user WHERE score < :userScore ORDER BY score DESC LIMIT :behind)" +
        " UNION ALL " +
        "(SELECT * FROM user WHERE score > :userScore ORDER BY score ASC LIMIT :front)",
        nativeQuery = true)
    List<User> usersScoreRaced(@Param("userScore") Long userScore, @Param("front") int fowrardCount, @Param("behind") int backwardCount);

    Page<User> findUserBySchool_SchoolName(String searchedSchoolName, Pageable pageable);

    Page<User> findUserByRegion_RegionName(String regionName, Pageable pageable);

    @Query("select u.refreshToken from User u where u.nickname = :nickname")
    Optional<String> findRefreshTokenByNickname(String nickname);


    Page<User> findUserByGrade(Grade grade, Pageable pageable);
}
