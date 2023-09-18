/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.dataModel.dto;

import io.github.navjotsrakhra.eventmanager.dataModel.Role;

import java.util.List;

/**
 * The UserDTO record represents an exposed version of a UserObject, providing simplified access to user data.
 */
public record UserDTO(long ID, String username, List<Role> roles, boolean accountLocked,
                      boolean accountExpired, boolean credentialsExpired) {

}
