package kr.hanjari.backend.config;

import kr.hanjari.backend.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    private final String[] SERVICE_ADMIN_URL = {
            "/api/service-announcements/service-admin", "/api/service-announcements/service-admin/**",
            "/api/clubs/service-admin", "/api/clubs/service-admin/**"
    };

    private final String[] UNION_ADMIN_URL = {
            "/api/announcements/union-admin", "/api/announcements/union-admin/**",
            "/api/documents/union-admin", "/api/documents/union-admin/**"
    };

    private final String[] CLUB_ADMIN_URL = {
            "/api/clubs/club-admin", "/api/clubs/club-admin/**",
            "/api/activities/club-admin", "/api/activities/club-admin/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // csrf, cors
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))

                // FormLogin, HttpBasic 인증 비활성화
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // Session 비활성화
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Filter 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // 엔드포인트 접근 제한
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers(SERVICE_ADMIN_URL).hasRole("SERVICE_ADMIN")
                                .requestMatchers(UNION_ADMIN_URL).hasRole("UNION_ADMIN")
                                .requestMatchers(CLUB_ADMIN_URL).hasRole("CLUB_ADMIN")
                                .anyRequest().permitAll()
                );

        return http.build();

    }
}
