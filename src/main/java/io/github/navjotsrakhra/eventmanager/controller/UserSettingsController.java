/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.controller;

import io.github.navjotsrakhra.eventmanager.service.UserSettingsService;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * The UserSettingsController class handles HTTP requests related to user settings.
 */
@RestController
@RequestMapping("/settings/user")
public class UserSettingsController {
    private final UserSettingsService userSettingsService;
    private final Logger LOG = org.slf4j.LoggerFactory.getLogger(UserSettingsController.class);

    /**
     * Constructor for the UserSettingsController class.
     *
     * @param userSettingsService Service for user settings.
     */
    public UserSettingsController(UserSettingsService userSettingsService) {
        this.userSettingsService = userSettingsService;
    }

    /**
     * Handles POST requests for the "/settings/user/password" URL to change the user's password.
     *
     * @param newPassword The new password as a String.
     * @param principal   Represents the currently authenticated user.
     * @return ResponseEntity indicating the result of the password change operation.
     */
    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody String newPassword, Principal principal) {
        LOG.info("Changing password for user: {}", principal.getName());
        return userSettingsService.changePassword(principal, newPassword);
    }
}
