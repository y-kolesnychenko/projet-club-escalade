package com.yvan.projetclubescalade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(config -> {
            config.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll();
            // Pages dispo visiteurs
            config.requestMatchers(
                    "/", "/categories", "/categories/**",
                    "/excursions", "/excursions/search", "/excursions/detail/**",
                    "/h2-console/**", "/webjars/**", "/css/**", "/forgot-password"
            ).permitAll();
            // Reste des pages
            config.anyRequest().authenticated();
        });

        http.formLogin(config -> {
            config.loginPage("/login");
            config.defaultSuccessUrl("/", true);
            config.permitAll();
        });

        http.logout(config -> {
            config.logoutSuccessUrl("/");
            config.permitAll();
        });

        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));
        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
}