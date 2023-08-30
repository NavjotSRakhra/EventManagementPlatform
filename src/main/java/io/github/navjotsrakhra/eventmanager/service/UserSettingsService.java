/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved
 */

package io.github.navjotsrakhra.eventmanager.service;

import io.github.navjotsrakhra.eventmanager.dataModel.UserObject;
import io.github.navjotsrakhra.eventmanager.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class UserSettingsService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserSettingsService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public ResponseEntity<?> changePassword(Principal principal, String newPassword) {
        Optional<UserObject> user = Optional.ofNullable(repository.findByUsername(principal.getName()));
        if (user.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Boolean.FALSE);

        user.get().setPassword(encoder.encode(newPassword));
        repository.save(user.get());

        return ResponseEntity.ok(Boolean.TRUE);
    }
}
