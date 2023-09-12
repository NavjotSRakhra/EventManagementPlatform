/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.service;

import io.github.navjotsrakhra.eventmanager.dataModel.EventPost;
import io.github.navjotsrakhra.eventmanager.exception.DateValidationFailedException;
import io.github.navjotsrakhra.eventmanager.exception.PostNotFoundException;
import io.github.navjotsrakhra.eventmanager.repository.EventPostRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventPostEditService {
    private final EventPostRepository repository;

    public EventPostEditService(EventPostRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<?> updatePostById(Long ID, @Valid EventPost postRecord) throws PostNotFoundException, DateValidationFailedException {
        Optional<EventPost> post = repository.findById(ID);
        if (post.isPresent()) {
            var eventPost = post.get();

            eventPost.setContent(postRecord.getContent());
            eventPost.setTitle(postRecord.getTitle());
            eventPost.setStartDay(postRecord.getStartDay());
            eventPost.setEndDay(postRecord.getEndDay());
            eventPost.setLocation(postRecord.getLocation());
            eventPost.setStartTime(postRecord.getStartTime());
            eventPost.setEndTime(postRecord.getEndTime());

            repository.save(eventPost);

            return ResponseEntity
                    .ok()
                    .build();
        }
        throw new PostNotFoundException();
    }
}
