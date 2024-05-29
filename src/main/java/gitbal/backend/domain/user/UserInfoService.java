package gitbal.backend.domain.user;

import org.springframework.http.ResponseEntity;

public interface UserInfoService {
    ResponseEntity<String> requestUserInfo(String username);

    ResponseEntity<String> requestUserRecentCommit(String username);
}
