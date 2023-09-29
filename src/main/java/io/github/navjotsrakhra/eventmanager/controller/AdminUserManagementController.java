/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.controller;

import io.github.navjotsrakhra.eventmanager.service.UserManagementService;
import io.github.navjotsrakhra.eventmanager.user.authentication.data.model.Role;
import io.github.navjotsrakhra.eventmanager.user.authentication.data.model.dto.UserDTO;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * The AdminUserManagementController class handles HTTP requests related to user management in the admin panel.
 */
@RestController
@RequestMapping("/admin")
public class AdminUserManagementController {

    private final UserManagementService userManagementService;
    private final Logger LOG = org.slf4j.LoggerFactory.getLogger(AdminUserManagementController.class);

    /**
     * Constructor for the AdminUserManagementController class.
     *
     * @param userManagementService Service for managing user roles.
     */
    public AdminUserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    /**
     * Handles GET requests for the "/admin/users" URL and retrieves a paginated list of all users.
     * Paginated using {@link Pageable}. Defaults to page 0, size 5, sorted by username.
     *
     * @return ResponseEntity containing a list of UserDTO objects.
     */
    @GetMapping("/users")
    public ResponseEntity<Page<UserDTO>> getUsers(@PageableDefault(size = 5, sort = "username", direction = Sort.Direction.ASC) Pageable pageable) {
        LOG.info("Getting all users, pageable: {}", pageable);
        return userManagementService.getAllUsersWithPagination(pageable);
    }

    /**
     * Handles POST requests for the "/admin/updateUserRole/{id}" URL to update user roles.
     *
     * @param id           The ID of the user whose roles need to be updated.
     * @param updatedRoles The list of updated roles for the user.
     * @return ResponseEntity indicating the result of the operation.
     */
    @PostMapping("/updateUserRole/{id}")
    public ResponseEntity<Boolean> updateUserRole(@PathVariable Long id, @RequestBody List<Role> updatedRoles) {
        LOG.info("Updating user roles for user with ID: {}, updated roles: {}", id, updatedRoles);
        return userManagementService.updateUserRoles(id, updatedRoles);
    }
}
