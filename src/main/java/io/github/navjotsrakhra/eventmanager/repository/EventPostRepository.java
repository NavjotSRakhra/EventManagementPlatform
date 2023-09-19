/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.repository;

import io.github.navjotsrakhra.eventmanager.dataModel.EventPost;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The EventPostRepository interface provides data access methods for interacting with EventPost entities.
 */
@Repository
public interface EventPostRepository extends JpaRepository<EventPost, Long> {

    /**
     * Find all event posts by the username of the user who posted them.
     *
     * @param postedBy The username of the user who posted the event posts.
     * @param pageable The pagination object. See {@link Pageable}.
     * @return A Page containing all event posted by the specified user.
     */
    Page<EventPost> findEventPostByPostedBy(@NotNull String postedBy, Pageable pageable);
}
