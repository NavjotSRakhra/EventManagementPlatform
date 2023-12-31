/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.service;

import io.github.navjotsrakhra.eventmanager.controller.PageController;
import io.github.navjotsrakhra.eventmanager.dataModel.dto.RegistrationFormDTO;
import io.github.navjotsrakhra.eventmanager.exception.UserNameTakenException;
import io.github.navjotsrakhra.eventmanager.user.authentication.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The UserRegistrationService class provides methods for user registration and saving user data from registration forms.
 */
@Service
public class UserRegistrationService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final Logger LOG = org.slf4j.LoggerFactory.getLogger(UserRegistrationService.class);

    /**
     * Constructor for the UserRegistrationService class.
     *
     * @param repository The UserRepository used for saving user data.
     * @param encoder    The PasswordEncoder used for encoding user passwords.
     */
    public UserRegistrationService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    /**
     * Save a user from a registration form with the default role.
     *
     * @param registrationFormDTO The RegistrationFormDTO object containing user registration data.
     * @return A redirection to {@link PageController#login()} upon successful registration.
     * @throws UserNameTakenException If the username is already taken.
     */
    public String saveUserFromRegistrationFormWIthDefaultRole(RegistrationFormDTO registrationFormDTO) throws UserNameTakenException {
        var user = registrationFormDTO.toUserObject(encoder);
        try {
            repository.save(user);
        } catch (Exception e) {
            if (e.getMessage().contains("duplicate key value violates")) {
                LOG.trace("Username taken: {}", user.getUsername(), e);
                throw new UserNameTakenException();
            } else throw e;
        }
        LOG.info("User registered: {}", user.getUsername());
        return "redirect:/login";
    }
}
