package gitbal.backend.repository;

import gitbal.backend.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickname);

    @Query("select u.profile_img from User u where u.nickname = :nickname")
    Optional<String> findProfileImgByNickname(@Param("nickname") String nickname);
}
