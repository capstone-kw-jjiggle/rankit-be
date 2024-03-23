package gitbal.backend.security;

import gitbal.backend.domain.User;
import gitbal.backend.repository.UserRepository;
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
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return mappingCustomeUserDetails(oAuth2User);

    }

    //oAuth2 빼와서 User로 매핑 하고 회원가입 및 로그인 진행
    private OAuth2User mappingCustomeUserDetails(OAuth2User oAuth2User) {

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String username = attributes.get("login").toString();
        String avatarUrl= attributes.get("avatar_url").toString();
        log.info(username);
        User user = userRepository.findByNickname(username)
            .orElse(createUser(username,avatarUrl));

        CustomUserDetails customUserDetails = CustomUserDetails.create(user);
        customUserDetails.setAttributes(attributes);
        return customUserDetails;
    }

    private User createUser(String username, String avatar_url) {

        log.info("현재 유저를 생성하려고 합니다.");
        User user = User.of(username, avatar_url);


        return userRepository.findByNickname(username).orElseGet(() ->{
            userRepository.save(user);
            return user;
            }
        );
    }
}
