/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.dataModel.dto;

import io.github.navjotsrakhra.eventmanager.dataModel.Role;
import io.github.navjotsrakhra.eventmanager.dataModel.UserObject;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * The RegistrationFormDTO class represents a user registration form containing username and password.
 */
public record RegistrationFormDTO(String username, String password) {
    /**
     * Converts this RegistrationForm to a UserObject.
     *
     * @param passwordEncoder The PasswordEncoder to encode the user's password.
     * @return UserObject created from the RegistrationForm.
     */
    public UserObject toUserObject(PasswordEncoder passwordEncoder) {
        return new UserObject(username, List.of(Role.USER), passwordEncoder.encode(password), false, false, false);
    }
}
