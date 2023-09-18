/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.service;

import io.github.navjotsrakhra.eventmanager.dataModel.EventPost;
import io.github.navjotsrakhra.eventmanager.dataModel.dto.EventPostDTO;
import io.github.navjotsrakhra.eventmanager.exception.DateValidationFailedException;
import io.github.navjotsrakhra.eventmanager.exception.PostNotFoundException;
import io.github.navjotsrakhra.eventmanager.repository.EventPostRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The {@code EventPostEditService} class is a Spring service responsible for updating event posts.
 *
 * @author Navjot Singh Rakhra
 * @version 1.0
 */
@Service
public class EventPostEditService {
    private final EventPostRepository repository;

    public EventPostEditService(EventPostRepository repository) {
        this.repository = repository;
    }

    /**
     * Updates an existing event post with the specified ID.
     *
     * @param ID         The ID of the event post to update.
     * @param postRecord The updated event post data.
     * @return A ResponseEntity containing the updated event post information if the update is successful.
     * @throws PostNotFoundException         If the specified event post ID is not found.
     * @throws DateValidationFailedException If there is a validation error related to dates.
     */
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
                    .ok(new EventPostDTO(eventPost.getId(), eventPost.getTitle(), eventPost.getContent(), eventPost.getLocation(), eventPost.getStartDay(), eventPost.getEndDay(), eventPost.getStartTime(), eventPost.getEndTime()))
                    ;
        }
        throw new PostNotFoundException();
    }
}
