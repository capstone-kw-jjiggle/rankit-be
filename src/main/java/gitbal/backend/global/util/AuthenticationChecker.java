package gitbal.backend.global.util;

import gitbal.backend.global.exception.NotLoginedException;
import gitbal.backend.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
public class AuthenticationChecker {

    public String checkAndRetrieveNickname(Authentication authentication) {
        if (authentication == null) {
            throw new NotLoginedException();
        }
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        return principal.getNickname();
    }

}
