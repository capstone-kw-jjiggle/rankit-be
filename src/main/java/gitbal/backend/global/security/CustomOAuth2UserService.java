package gitbal.backend.global.security;

import gitbal.backend.domain.introduction.Introduction;
import gitbal.backend.domain.user.UserRepository;
import gitbal.backend.domain.user.User;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Hello CusotmOauth2UserService 입니다.");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return mappingCustomeUserDetails(oAuth2User);
    }

    //oAuth2 빼와서 User로 매핑 하고 회원가입 및 로그인 진행
    private OAuth2User mappingCustomeUserDetails(OAuth2User oAuth2User) {
        log.info("MappingCustomUserDetails 로 왔습니다.");
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String username = attributes.get("login").toString();
        String avatarUrl = attributes.get("avatar_url").toString();
        log.info("username은 {}", username);
        User user = userRepository.findByNickname(username)
            .orElse(createUser(username, avatarUrl));

        CustomUserDetails customUserDetails = settingUserDetails(user,
            attributes);
        log.info(customUserDetails.toString());

        return customUserDetails;
    }

    private static CustomUserDetails settingUserDetails(User user,
        Map<String, Object> attributes) {
        CustomUserDetails customUserDetails = CustomUserDetails.create(user);
        customUserDetails.setAttributes(attributes);
        return customUserDetails;
    }

    private User createUser(String username, String avatarUrl) {
        log.info("현재 유저를 생성하려고 합니다.");
        User user = User.of(username, avatarUrl);
        //TODO : 점수 계산 시점을 여기로 잡아야할 지? 고민

        return userRepository.findByNickname(username).orElseGet(() -> {
                userRepository.save(user);
                return user;
            }
        );
    }
}
