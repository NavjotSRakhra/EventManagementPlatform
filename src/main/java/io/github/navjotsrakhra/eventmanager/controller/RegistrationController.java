/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved
 */

package io.github.navjotsrakhra.eventmanager.controller;

import io.github.navjotsrakhra.eventmanager.dataModel.RegistrationForm;
import io.github.navjotsrakhra.eventmanager.exception.UserNameTakenException;
import io.github.navjotsrakhra.eventmanager.service.UserRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private final UserRegistrationService userRegistrationService;

    public RegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping
    public String register(@RequestBody RegistrationForm registrationForm) throws UserNameTakenException {
        return userRegistrationService.saveUserFromRegistrationFormWIthDefaultRole(registrationForm);
    }

    @ExceptionHandler(value = UserNameTakenException.class)
    public ResponseEntity<String> usernameTakenException(UserNameTakenException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
