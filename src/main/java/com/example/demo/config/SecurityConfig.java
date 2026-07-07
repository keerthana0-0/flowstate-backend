package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http.csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth

                // ── Public endpoints ──
                .requestMatchers("/api/users/register", "/api/users/login").permitAll()

                // ── User management ──
                .requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAnyRole("ADMIN", "PRACTITIONER")
                .requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("ADMIN", "COACH", "PRACTITIONER")

                // ── Focus Sessions ──
                .requestMatchers(HttpMethod.POST, "/api/focus-sessions/**").hasAnyRole("ADMIN", "PRACTITIONER")
                .requestMatchers(HttpMethod.PUT, "/api/focus-sessions/**").hasAnyRole("ADMIN", "PRACTITIONER")
                .requestMatchers(HttpMethod.DELETE, "/api/focus-sessions/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/focus-sessions/**").hasAnyRole("ADMIN", "COACH", "PRACTITIONER")

                // ── Session Goals ──
                .requestMatchers(HttpMethod.POST, "/api/session-goals/**").hasAnyRole("ADMIN", "PRACTITIONER")
                .requestMatchers(HttpMethod.PUT, "/api/session-goals/**").hasAnyRole("ADMIN", "PRACTITIONER")
                .requestMatchers(HttpMethod.DELETE, "/api/session-goals/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/session-goals/**").hasAnyRole("ADMIN", "COACH", "PRACTITIONER")

                // ── Focus Blocks ──
                .requestMatchers(HttpMethod.POST, "/api/focus-blocks/**").hasAnyRole("ADMIN", "PRACTITIONER")
                .requestMatchers(HttpMethod.PUT, "/api/focus-blocks/**").hasAnyRole("ADMIN", "PRACTITIONER")
                .requestMatchers(HttpMethod.DELETE, "/api/focus-blocks/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/focus-blocks/**").hasAnyRole("ADMIN", "COACH", "PRACTITIONER")

                // ── Distraction Logs ──
                .requestMatchers(HttpMethod.POST, "/api/distraction-logs/**").hasAnyRole("ADMIN", "PRACTITIONER")
                .requestMatchers(HttpMethod.PUT, "/api/distraction-logs/**").hasAnyRole("ADMIN", "PRACTITIONER")
                .requestMatchers(HttpMethod.DELETE, "/api/distraction-logs/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/distraction-logs/**").hasAnyRole("ADMIN", "COACH", "PRACTITIONER")

                // ── Coach Recommendations ──
                .requestMatchers(HttpMethod.POST, "/api/recommendations/**").hasAnyRole("ADMIN", "COACH")
                .requestMatchers(HttpMethod.PUT, "/api/recommendations/**").hasAnyRole("ADMIN", "COACH", "PRACTITIONER")
                .requestMatchers(HttpMethod.DELETE, "/api/recommendations/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/recommendations/**").hasAnyRole("ADMIN", "COACH", "PRACTITIONER")

                // ── All other requests require authentication ──
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}