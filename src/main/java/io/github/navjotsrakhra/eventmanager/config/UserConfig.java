/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved
 */

package io.github.navjotsrakhra.eventmanager.config;

import io.github.navjotsrakhra.eventmanager.dataModel.UserObject;
import io.github.navjotsrakhra.eventmanager.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class UserConfig {
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            UserObject userObject = userRepository.findByUsername(username);
            if (userObject == null) throw new UsernameNotFoundException("Username: " + username + " not found.");

            return userObject;
        };
    }
}
