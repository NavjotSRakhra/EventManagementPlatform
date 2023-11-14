/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.config;

import io.github.navjotsrakhra.eventmanager.service.UserManagementService;
import io.github.navjotsrakhra.eventmanager.user.authentication.data.model.Role;
import jakarta.servlet.http.HttpServletResponse;
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
    private final UserManagementService userManagementService;

    public SecurityConfig(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

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
                        .requestMatchers("/admin/**", "/actuator/**", "/error").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/user/events/post").hasAnyAuthority(Role.MANAGEMENT.name(), Role.ADMIN.name())
                        .requestMatchers("/events/**", "/login", "/register", "/main-H24H4IR3.js", "/polyfills-LZBJRJJE.js", "/styles-BGSM6UNS.css").permitAll()
                        .anyRequest().authenticated())
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            response.setHeader("roles", authentication.getAuthorities().toString());
                        })
                        .failureHandler((request, response, exception) -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
                        .permitAll())
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .deleteCookies("JSESSIONID"));

        return httpSecurity.build();
    }
}
