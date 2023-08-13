/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved
 */

package io.github.navjotsrakhra.eventmanager.repository;

import io.github.navjotsrakhra.eventmanager.dataModel.UserObject;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserObject, Long> {
    UserObject findByUsername(String username);
}
