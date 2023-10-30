/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.service;

import io.github.navjotsrakhra.eventmanager.user.authentication.data.model.Role;
import io.github.navjotsrakhra.eventmanager.user.authentication.data.model.UserObject;
import io.github.navjotsrakhra.eventmanager.user.authentication.data.model.dto.UserDTO;
import io.github.navjotsrakhra.eventmanager.user.authentication.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final Logger LOG = org.slf4j.LoggerFactory.getLogger(UserManagementService.class);

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
     * @deprecated Use {@link #getAllUsersWithPagination(Pageable)} instead.
     */
    @Deprecated
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        LOG.warn("getAllUsers() is deprecated. Use getAllUsersWithPagination() instead.");
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
        LOG.info("Updating user roles, id: {}, updatedRoles: {}", id, updatedRoles);

        Optional<UserObject> userToBeUpdated = userRepository.findById(id);
        if (userToBeUpdated.isEmpty()) {
            LOG.warn("User with ID: {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Boolean.FALSE);
        }
        List<Role> userRoles = userToBeUpdated.get().getRoles();
        LOG.info("User roles before update: {}", userRoles);
        userRoles.removeIf(e -> true);
        userRoles.addAll(updatedRoles);

        userRepository.save(userToBeUpdated.get());
        LOG.info("User roles after update: {}", userRoles);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    /**
     * Get a list of all users with pagination. See {@link Pageable}. Defaults to page 0, size 5, sorted by username.
     *
     * @param pageable The pagination object. See {@link Pageable}.
     * @return ResponseEntity containing a list of UserDTO objects.
     */

    public ResponseEntity<Page<UserDTO>> getAllUsersWithPagination(Pageable pageable) {
        return ResponseEntity.ok(
                userRepository.findAll(pageable)
                        .map(
                                e -> new UserDTO(e.getId(), e.getUsername(), e.getRoles(), e.isAccountLocked(), e.isAccountExpired(), e.isCredentialsExpired())
                        )
        );
    }
}
