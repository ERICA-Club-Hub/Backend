package kr.hanjari.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("prod")
public class ProdCorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        List<String> allowedOrigins = new ArrayList<>();
        allowedOrigins.add("https://hanjari.site");

        List<String> allowedMethods = new ArrayList<>();
        allowedMethods.add("OPTIONS");
        allowedMethods.add("GET");
        allowedMethods.add("POST");
        allowedMethods.add("PUT");
        allowedMethods.add("PATCH");
        allowedMethods.add("DELETE");

        List<String> allowedHeaders = new ArrayList<>();
        allowedHeaders.add("Content-Type");
        allowedHeaders.add("Authorization");

        List<String> exposedHeaders = new ArrayList<>();
        exposedHeaders.add("Authorization");

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(allowedMethods);
        configuration.setAllowedHeaders(allowedHeaders);
        configuration.setExposedHeaders(exposedHeaders);
        configuration.setMaxAge(3600L); // 1H

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
