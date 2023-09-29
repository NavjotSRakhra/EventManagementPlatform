/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.service;

import io.github.navjotsrakhra.eventmanager.dataModel.Role;
import io.github.navjotsrakhra.eventmanager.dataModel.UserObject;
import io.github.navjotsrakhra.eventmanager.dataModel.dto.UserDTO;
import io.github.navjotsrakhra.eventmanager.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The UserManagementService class provides methods for managing user-related operations.
 */
@Service
public class UserManagementService {
    private final UserRepository userRepository;

    /**
     * Constructor for the UserManagementService class.
     *
     * @param userRepository The UserRepository used for managing user data.
     */
    public UserManagementService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get a list of all users.
     *
     * @return ResponseEntity containing a list of UserDTO objects representing users.
     */
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll().stream().map(e -> new UserDTO(e.getId(), e.getUsername(), e.getRoles(), e.isAccountLocked(), e.isAccountExpired(), e.isCredentialsExpired())).toList());
    }

    /**
     * Update the roles of a user with the specified ID.
     *
     * @param id           The ID of the user to be updated.
     * @param updatedRoles The updated list of roles for the user.
     * @return ResponseEntity indicating the result of the operation.
     */
    public ResponseEntity<Boolean> updateUserRoles(Long id, List<Role> updatedRoles) {

        Optional<UserObject> userToBeUpdated = userRepository.findById(id);
        if (userToBeUpdated.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Boolean.FALSE);

        List<Role> userRoles = userToBeUpdated.get().getRoles();
        userRoles.removeIf(e -> true);
        userRoles.addAll(updatedRoles);

        userRepository.save(userToBeUpdated.get());

        return ResponseEntity.ok(Boolean.TRUE);
    }

}
