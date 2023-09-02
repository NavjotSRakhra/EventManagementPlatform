/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.service;

import io.github.navjotsrakhra.eventmanager.dataModel.EventPost;
import io.github.navjotsrakhra.eventmanager.repository.EventPostRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * The EventPostAddService class provides methods for adding new EventPost entities to the repository.
 */
@Service
public class EventPostAddService {
    private final EventPostRepository repository;

    /**
     * Constructor for the EventPostAddService class.
     *
     * @param repository The EventPostRepository used for storing event posts.
     */
    public EventPostAddService(EventPostRepository repository) {
        this.repository = repository;
    }

    /**
     * Add a new event post to the repository.
     *
     * @param event The EventPost object to be added, validated using the @Valid annotation.
     * @return ResponseEntity indicating the result of the operation.
     */
    public ResponseEntity<?> addEvent(@Valid EventPost event) {
        repository.save(event);
        return ResponseEntity
                .ok()
                .build();
    }
}
