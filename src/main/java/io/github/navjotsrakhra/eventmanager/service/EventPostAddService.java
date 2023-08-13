/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved
 */

package io.github.navjotsrakhra.eventmanager.service;

import io.github.navjotsrakhra.eventmanager.dataModel.EventPost;
import io.github.navjotsrakhra.eventmanager.repository.EventPostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EventPostAddService {
    private final EventPostRepository repository;

    public EventPostAddService(EventPostRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<?> addEvent(EventPost event) {
        repository.save(event);
        return ResponseEntity
                .ok()
                .build();
    }
}
