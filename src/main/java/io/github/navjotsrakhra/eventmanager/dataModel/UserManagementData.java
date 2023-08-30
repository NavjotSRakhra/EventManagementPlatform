/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved
 */

package io.github.navjotsrakhra.eventmanager.dataModel;

import java.util.List;

public record UserManagementData(long ID, String username, List<Role> roles, boolean accountLocked,
                                 boolean accountExpired, boolean credentialsExpired) {

}
