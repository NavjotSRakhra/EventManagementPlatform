/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved
 */

package io.github.navjotsrakhra.eventmanager.service;

import io.github.navjotsrakhra.eventmanager.dataModel.Role;
import io.github.navjotsrakhra.eventmanager.dataModel.UserManagementData;
import io.github.navjotsrakhra.eventmanager.dataModel.UserObject;
import io.github.navjotsrakhra.eventmanager.repository.UserRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserManagementService {
    private final ListCrudRepository<UserObject, Long> userRepository;

    public UserManagementService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<List<UserManagementData>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll().stream().map(e -> new UserManagementData(e.getId(), e.getUsername(), e.getRoles(), e.isAccountLocked(), e.isAccountExpired(), e.isCredentialsExpired())).toList());
    }

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
