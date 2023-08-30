/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved
 */

package io.github.navjotsrakhra.eventmanager.service;

import io.github.navjotsrakhra.eventmanager.dataModel.RegistrationForm;
import io.github.navjotsrakhra.eventmanager.exception.UserNameTakenException;
import io.github.navjotsrakhra.eventmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserRegistrationService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public String saveUserFromRegistrationFormWIthDefaultRole(RegistrationForm registrationForm) throws UserNameTakenException {
        try {
            repository.save(registrationForm.toUserObject(encoder));
        } catch (Exception e) {
            if (e.getMessage().contains("duplicate key value violates")) throw new UserNameTakenException();
            else throw e;

        }
        return "redirect:/login";
    }
}
