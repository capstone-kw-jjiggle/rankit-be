package gitbal.backend.api.auth.service;

import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserRepository;
import gitbal.backend.global.exception.NotFoundUserException;
import gitbal.backend.global.security.jwt.JwtTokenProvider;
import java.net.URI;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private static final String FE_URL ="http://localhost:5173/auth/token";
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;


    public String madeRedirectUrl(String username) {
        User user = findUser(username);
        log.info("username : {}", user.getNickname());

        String redirectUrl = getRedirectUrl(username, isRegisterUser(user));

        return "redirect:" + redirectUrl;
    }

    private boolean isRegisterUser(User user) {
        return (!Objects.isNull(user.getSchool()) && !Objects.isNull(user.getRegion()));
    }

    private User findUser(String username) {
        return userRepository.findByNickname(username)
            .orElseThrow(NotFoundUserException::new);
    }

    private String getRedirectUrl(String username, boolean isRegistered) {
        return UriComponentsBuilder.fromUri(URI.create(FE_URL))
            .queryParam("accessToken", jwtTokenProvider.createAccessToken(username))
            .queryParam("isRegistered", isRegistered)
            .build()
            .toString();
    }


}
