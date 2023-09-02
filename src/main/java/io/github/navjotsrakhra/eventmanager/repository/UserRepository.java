/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.repository;

import io.github.navjotsrakhra.eventmanager.dataModel.UserObject;
import org.springframework.data.repository.ListCrudRepository;

/**
 * The UserRepository interface provides data access methods for interacting with UserObject entities.
 */
public interface UserRepository extends ListCrudRepository<UserObject, Long> {
    
    /**
     * Find a user by their username.
     *
     * @param username The username to search for.
     * @return The UserObject with the specified username, or null if not found.
     */
    UserObject findByUsername(String username);
}
