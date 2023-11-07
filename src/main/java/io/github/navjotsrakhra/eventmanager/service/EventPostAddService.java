/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.service;

import io.github.navjotsrakhra.eventmanager.dataModel.EventPost;
import io.github.navjotsrakhra.eventmanager.repository.EventPostRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * The EventPostAddService class provides methods for adding new EventPost entities to the repository.
 */
@Service
public class EventPostAddService {
    private final EventPostRepository repository;
    private final Logger LOG = org.slf4j.LoggerFactory.getLogger(EventPostAddService.class);

    /**
     * Constructor for the EventPostAddService class.
     *
     * @param repository The EventPostRepository used for storing event posts.
     */
    public EventPostAddService(EventPostRepository repository) {
        this.repository = repository;
    }

    /**
     * Add a new event post to the repository. The postedBy field is set to the username of the currently logged-in user.
     *
     * @param event     The EventPost object to be added, validated using the @Valid annotation.
     * @param principal The Principal object containing the username of the user making the request.
     * @return ResponseEntity indicating the result of the operation.
     */
    public ResponseEntity<?> addEvent(@Valid EventPost event, Principal principal) {
        event.setPostedBy(principal.getName());
        LOG.info("Adding event: {}, user adding: {}", event, principal.getName());
        var response = repository.save(event);
        return ResponseEntity
                .ok()
                .body(response.getId());
    }
}
