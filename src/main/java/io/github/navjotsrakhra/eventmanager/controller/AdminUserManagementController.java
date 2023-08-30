/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved
 */

package io.github.navjotsrakhra.eventmanager.controller;

import io.github.navjotsrakhra.eventmanager.dataModel.Role;
import io.github.navjotsrakhra.eventmanager.dataModel.UserManagementData;
import io.github.navjotsrakhra.eventmanager.service.UserManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminUserManagementController {

    private final UserManagementService userManagementService;

    public AdminUserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserManagementData>> getUsers() {
        return userManagementService.getAllUsers();
    }

    @PostMapping("/updateUserRole/{id}")
    public ResponseEntity<Boolean> updateUserRole(@PathVariable Long id, @RequestBody List<Role> updatedRoles) {
        return userManagementService.updateUserRoles(id, updatedRoles);
    }
}
