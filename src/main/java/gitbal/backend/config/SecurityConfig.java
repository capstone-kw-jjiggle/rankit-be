package gitbal.backend.config;

import gitbal.backend.security.CustomOAuth2UserService;
import gitbal.backend.security.OAuth2AuthenticationSuccessHandler;
import gitbal.backend.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.
            csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                auth -> {
                    auth.requestMatchers("/swagger-ui/**", "/").permitAll();
                    auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                        .permitAll();
                    auth.requestMatchers("/api/v1/loginCheck")
                        .authenticated(); // TODO : 임의의 로직으로 loginChecking 진행중임.
                    auth.anyRequest().permitAll();
                }
            )
            .oauth2Login(oauth2 -> {
                oauth2.userInfoEndpoint(user -> user.userService(customOAuth2UserService));
                oauth2.successHandler(oAuth2AuthenticationSuccessHandler);
            })
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

}
