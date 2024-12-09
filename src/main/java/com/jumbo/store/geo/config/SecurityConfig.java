package com.jumbo.store.geo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/v1/**",
                    "/swagger-ui.html", 
                    "/v1/api-docs/**", 
                    "/swagger-ui/**",
                    "/actuator/health"
                    ).permitAll() 
                .anyRequest().authenticated() 
            )
            .httpBasic(Customizer.withDefaults()); 
        return http.build();
    }
}