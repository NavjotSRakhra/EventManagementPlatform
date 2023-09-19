/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.config;

import io.github.navjotsrakhra.eventmanager.dataModel.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * The SecurityConfig class is responsible for configuring security settings and access control for the application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /**
     * Configure the password encoder for encoding and validating passwords.
     *
     * @return A BCryptPasswordEncoder for password encoding.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configure security settings and access control for the application.
     *
     * @param httpSecurity The HttpSecurity object to configure security settings.
     * @return A SecurityFilterChain that defines the security configuration for the application.
     * @throws Exception If there are configuration errors.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .requestMatchers("/admin/**", "/actuator/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/user/events/post").hasAuthority(Role.MANAGEMENT.name())
                        .requestMatchers("/events/**", "/login", "/register").permitAll()
                        .anyRequest().authenticated())
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .permitAll()
                        .defaultSuccessUrl("/events"))
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .deleteCookies("JSESSIONID"));

        return httpSecurity.build();
    }
}
