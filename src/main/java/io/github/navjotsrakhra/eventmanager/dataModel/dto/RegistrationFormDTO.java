/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.dataModel.dto;

import io.github.navjotsrakhra.eventmanager.user.authentication.data.model.Role;
import io.github.navjotsrakhra.eventmanager.user.authentication.data.model.UserObject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * The RegistrationFormDTO class represents a user registration form containing username and password.
 */
public record RegistrationFormDTO(
        @NotNull(message = "The username must not be null") @NotBlank(message = "The username must not be blank") String username,
        @NotNull(message = "The password cannot be null") @NotBlank(message = "The password cannot be blank") String password) {
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
