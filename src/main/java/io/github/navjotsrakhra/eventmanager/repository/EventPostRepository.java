/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.repository;

import io.github.navjotsrakhra.eventmanager.dataModel.EventPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The EventPostRepository interface provides data access methods for interacting with EventPost entities.
 */
@Repository
public interface EventPostRepository extends JpaRepository<EventPost, Long> {
}
