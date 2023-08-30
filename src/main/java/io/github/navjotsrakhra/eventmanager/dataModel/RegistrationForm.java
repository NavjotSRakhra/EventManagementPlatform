/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved
 */

package io.github.navjotsrakhra.eventmanager.dataModel;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Data
public class RegistrationForm {
    private final String username;
    private final String password;
    private List<Role> roles;

    public UserObject toUserObject(PasswordEncoder passwordEncoder) {
        return new UserObject(username, List.of(Role.USER), passwordEncoder.encode(password), false, false, false);
    }
}
