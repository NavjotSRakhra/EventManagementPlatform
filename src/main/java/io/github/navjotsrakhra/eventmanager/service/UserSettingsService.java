/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.service;

import io.github.navjotsrakhra.eventmanager.user.authentication.data.model.UserObject;
import io.github.navjotsrakhra.eventmanager.user.authentication.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Optional;

/**
 * The UserSettingsService class provides methods for managing user settings, including password changes.
 */
@Service
public class UserSettingsService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final Logger LOG = org.slf4j.LoggerFactory.getLogger(UserSettingsService.class);

    /**
     * Constructor for the UserSettingsService class.
     *
     * @param repository The UserRepository used for managing user data.
     * @param encoder    The PasswordEncoder used for encoding user passwords.
     */
    public UserSettingsService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    /**
     * Get the HttpHeaders required for redirection after a password change.
     *
     * @param path The path to redirect to.
     * @return The HttpHeaders required for redirection.
     */
    private static HttpHeaders redirectionHeader(CharSequence path) {
        HttpHeaders header = new HttpHeaders();
        header.setLocation(URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + path));
        return header;
    }

    /**
     * Change the password for the currently logged-in user.
     *
     * @param principal   The Principal object representing the currently logged-in user.
     * @param newPassword The new password to be set for the user.
     * @return ResponseEntity indicating the result of the password change operation.
     */
    public ResponseEntity<?> changePassword(Principal principal, String newPassword) {
        LOG.info("Changing password for user: {}", principal.getName());
        Optional<UserObject> user = Optional.ofNullable(repository.findByUsername(principal.getName()));
        if (user.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            LOG.warn("User not found or new password is empty or null");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        user.get().setPassword(encoder.encode(newPassword));
        repository.save(user.get());
        LOG.info("Password changed for user: {}", principal.getName());
        return new ResponseEntity<>(redirectionHeader("/logout"), HttpStatus.SEE_OTHER);
    }
}
