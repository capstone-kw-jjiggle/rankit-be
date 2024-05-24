package gitbal.backend.global.config;

import gitbal.backend.global.security.CustomLogoutHandler;
import gitbal.backend.global.security.CustomLogoutSuccessHandler;
import gitbal.backend.global.security.CustomOAuth2UserService;
import gitbal.backend.global.security.OAuth2AuthenticationSuccessHandler;
import gitbal.backend.global.security.jwt.JwtAuthenticationFilter;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomLogoutHandler customLogoutHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.
            cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                auth -> {
                    auth.requestMatchers("/swagger-ui/**", "/").permitAll();
                    auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                        .permitAll();
                    auth.requestMatchers("/api/v1/auth/logout","/api/v1/login", "/api/v1/join", "/api/v1/schoolRank/mySchool") // TODO : 로그인 필요한 uri 다 집어넣기
                        .authenticated();
                    auth.anyRequest().permitAll();
                }
            )
            .logout(logout -> {
                logout.logoutUrl("/api/v1/auth/logout");
                logout.addLogoutHandler(customLogoutHandler);
                logout.deleteCookies("JSESSIONID", "remember-me");
                logout.logoutSuccessHandler(customLogoutSuccessHandler);
            })
            .oauth2Login(oauth2 -> {
                oauth2.userInfoEndpoint(user -> user.userService(customOAuth2UserService));
                oauth2.successHandler(oAuth2AuthenticationSuccessHandler);
            })
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }





    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(Collections.singletonList("*")); // ⭐️ 허용할 origin
            config.setAllowCredentials(true);
            return config;
        };
    }

}
