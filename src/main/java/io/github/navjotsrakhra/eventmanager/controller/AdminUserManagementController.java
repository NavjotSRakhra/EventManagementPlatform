/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.controller;

import io.github.navjotsrakhra.eventmanager.dataModel.Role;
import io.github.navjotsrakhra.eventmanager.dataModel.exposed.UserData;
import io.github.navjotsrakhra.eventmanager.service.UserManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The AdminUserManagementController class handles HTTP requests related to user management in the admin panel.
 */
@Controller
@RequestMapping("/admin")
public class AdminUserManagementController {

    private final UserManagementService userManagementService;

    /**
     * Constructor for the AdminUserManagementController class.
     *
     * @param userManagementService Service for managing user roles.
     */
    public AdminUserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    /**
     * Handles GET requests for the "/admin/users" URL and retrieves a list of all users.
     *
     * @return ResponseEntity containing a list of UserManagementData objects.
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserData>> getUsers() {
        return userManagementService.getAllUsers();
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
        return userManagementService.updateUserRoles(id, updatedRoles);
    }
}
