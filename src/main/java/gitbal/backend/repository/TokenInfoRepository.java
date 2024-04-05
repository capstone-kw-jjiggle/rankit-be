package gitbal.backend.repository;

import gitbal.backend.entity.TokenInfo;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface TokenInfoRepository extends CrudRepository<TokenInfo, String> {
    Optional<TokenInfo> findByAccessToken(String accessToken);

}
