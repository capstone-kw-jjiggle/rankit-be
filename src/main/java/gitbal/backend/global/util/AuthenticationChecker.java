package gitbal.backend.global.util;

import gitbal.backend.exception.NotLoginedException;
import gitbal.backend.global.security.CustomUserDetails;
import org.springframework.security.core.Authentication;

public class AuthenticationChecker {

    private Authentication authentication;

    public AuthenticationChecker(Authentication authentication) {
        this.authentication=authentication;
    }

    public String checkAndRetrieveNickname() {
        if (authentication == null) {
            throw new NotLoginedException();
        }
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        return principal.getNickname();
    }

}
