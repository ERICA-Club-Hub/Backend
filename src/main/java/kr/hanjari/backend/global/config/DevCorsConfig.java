package kr.hanjari.backend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("dev")
public class DevCorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        List<String> allowedOrigins = new ArrayList<>();
        allowedOrigins.add("http://localhost:3000");
        allowedOrigins.add("http://localhost:4173");
        allowedOrigins.add("http://localhost:5173");
        allowedOrigins.add("https://localhost:3000");
        allowedOrigins.add("https://localhost:4173");
        allowedOrigins.add("https://localhost:5173");
        allowedOrigins.add("https://hanjari.netlify.app");
        allowedOrigins.add("https://develop.hanjari.site");
        allowedOrigins.add("https://dev.backend.hanjari.site:443");

        List<String> allowedOriginPatterns = new ArrayList<>();
        allowedOriginPatterns.add("https://deploy-preview-*--hanjari.netlify.app");

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
        configuration.setAllowedOriginPatterns(allowedOriginPatterns);
        configuration.setAllowedMethods(allowedMethods);
        configuration.setAllowedHeaders(allowedHeaders);
        configuration.setExposedHeaders(exposedHeaders);
        configuration.setMaxAge(3600L); // 1H

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
