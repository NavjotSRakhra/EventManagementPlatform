/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.controller;

import io.github.navjotsrakhra.eventmanager.dataModel.exposed.RegistrationForm;
import io.github.navjotsrakhra.eventmanager.exception.UserNameTakenException;
import io.github.navjotsrakhra.eventmanager.service.UserRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The RegistrationController class handles HTTP requests related to user registration.
 */
@RestController
@RequestMapping("/register")
public class RegistrationController {
    private final UserRegistrationService userRegistrationService;

    /**
     * Constructor for the RegistrationController class.
     *
     * @param userRegistrationService Service for user registration.
     */
    public RegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    /**
     * Handles POST requests for the "/register" URL to register a new user.
     *
     * @param registrationForm The RegistrationForm object containing user registration information. Specifically username and password
     * @return A String representing the registration result.
     * @throws UserNameTakenException if the username is already taken.
     */
    @PostMapping
    public String register(@RequestBody RegistrationForm registrationForm) throws UserNameTakenException {
        return userRegistrationService.saveUserFromRegistrationFormWIthDefaultRole(registrationForm);
    }

    /**
     * Handles exceptions related to a username already being taken.
     *
     * @param e The UserNameTakenException.
     * @return ResponseEntity with a CONFLICT status and an error message.
     */
    @ExceptionHandler(value = UserNameTakenException.class)
    public ResponseEntity<String> usernameTakenExceptionHandler(UserNameTakenException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
