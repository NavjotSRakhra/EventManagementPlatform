/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.service;

import io.github.navjotsrakhra.eventmanager.dataModel.EventPost;
import io.github.navjotsrakhra.eventmanager.dataModel.dto.EventPostDTO;
import io.github.navjotsrakhra.eventmanager.exception.DateValidationFailedException;
import io.github.navjotsrakhra.eventmanager.repository.EventPostRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
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
    private final Logger LOG = org.slf4j.LoggerFactory.getLogger(EventPostEditService.class);

    public EventPostEditService(EventPostRepository repository) {
        this.repository = repository;
    }

    /**
     * Updates an existing event post with the specified ID.
     *
     * @param ID               The ID of the event post to update.
     * @param newEventPostData The updated event post data.
     * @return A ResponseEntity containing the updated event post information if the update is successful.
     * If unsuccessful, returns a 404 Not Found status code.
     */
    public ResponseEntity<?> updatePostById(Long ID, @Valid EventPost newEventPostData) {
        Optional<EventPost> post = repository.findById(ID);
        LOG.info("Updating event post with ID: {}", ID);
        if (post.isPresent()) {
            var eventPost = post.get();
            LOG.info("Event post with ID: {} found, event: {}", ID, eventPost);
            try {
                return updateEventPost(newEventPostData, eventPost);
            } catch (DateValidationFailedException e) {
                LOG.trace("Date validation failed for event post with ID: {}", ID, e);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
        LOG.warn("Event post with ID: {} not found", ID);
        return ResponseEntity.notFound().build();
    }

    /**
     * Deletes an existing event post with the specified ID.
     *
     * @param ID The ID of the event post to delete.
     * @return A ResponseEntity containing no content if the deletion is successful.
     * If unsuccessful, returns a 404 Not Found status code.
     */
    public ResponseEntity<?> deletePostById(Long ID) {
        LOG.info("Deleting event post with ID: {}", ID);
        Optional<EventPost> post = repository.findById(ID);
        if (post.isPresent()) {
            LOG.info("Event post with ID: {} found, deleting", ID);
            repository.deleteById(ID);
            LOG.info("Event post with ID: {} deleted", ID);
            return ResponseEntity.ok().build();
        }
        LOG.warn("Event post with ID: {} not found", ID);
        return ResponseEntity.notFound().build();
    }

    /**
     * Updates an existing event post with the specified ID and checks if the user is authorized to edit the post.
     *
     * @param id         The ID of the event post to update.
     * @param postRecord The updated event post data.
     * @param principal  The Principal object containing the username of the user making the request.
     * @return A ResponseEntity containing the updated event post information if the update is successful.
     * If unsuccessful, returns a 404 Not Found or 403 Forbidden status code.
     */
    public ResponseEntity<?> updatePostById(Long id, @Valid EventPost postRecord, Principal principal) {
        LOG.info("Updating event post with ID: {}, new values: {}, user: {}", id, postRecord, principal.getName());
        Optional<EventPost> post = repository.findById(id);

        if (post.isPresent()) { // If the post exists
            var eventPost = post.get();
            LOG.info("Event post with ID: {} found, event: {}", id, eventPost);

            try {
                // Check if the user is authorized to edit this post
                if (eventPost.getPostedBy().equals(principal.getName())) {
                    LOG.info("User authorized to edit event post with ID: {}, user: {}", id, principal.getName());
                    return updateEventPost(postRecord, eventPost);
                } else {
                    LOG.warn("User not authorized to edit event post with ID: {}, user: {}", id, principal.getName());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to edit this post.");
                }
            } catch (DateValidationFailedException e) { // If date validation fails
                LOG.trace("Date validation failed for event post with ID: {}", id, e);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
        LOG.warn("Event post with ID: {} not found", id);
        return ResponseEntity.notFound().build(); // If the post does not exist
    }

    /**
     * Updates an existing event post with the specified ID.
     *
     * @param newEventPostData The updated event post data.
     * @param eventPost        The event post to update.
     * @return A ResponseEntity containing the updated event post information if the update is successful.
     * @throws DateValidationFailedException If there is a validation error related to dates.
     */
    private ResponseEntity<?> updateEventPost(@Valid EventPost newEventPostData, EventPost eventPost) throws DateValidationFailedException {
        eventPost.setTitle(newEventPostData.getTitle());
        eventPost.setContent(newEventPostData.getContent());
        eventPost.setEnrollmentLink(newEventPostData.getEnrollmentLink());
        eventPost.setImageLink(newEventPostData.getImageLink());
        eventPost.setStartDay(newEventPostData.getStartDay());
        eventPost.setEndDay(newEventPostData.getEndDay());
        eventPost.setLocation(newEventPostData.getLocation());
        eventPost.setStartTime(newEventPostData.getStartTime());
        eventPost.setEndTime(newEventPostData.getEndTime());

        repository.save(eventPost);
        LOG.info("Event successfully updated: {}", eventPost);

        return ResponseEntity
                .ok(new EventPostDTO(eventPost.getId(), eventPost.getTitle(), eventPost.getContent(), eventPost.getLocation(), eventPost.getEnrollmentLink(), eventPost.getImageLink(), eventPost.getStartDay(), eventPost.getEndDay(), eventPost.getStartTime(), eventPost.getEndTime()));
    }

    /**
     * Deletes an existing event post with the specified ID and checks if the user is authorized to delete the post.
     *
     * @param id        The ID of the event post to delete.
     * @param principal The Principal object containing the username of the user making the request.
     * @return A ResponseEntity containing no content if the deletion is successful.
     * If unsuccessful, returns a 404 Not Found or 403 Forbidden status code.
     */
    public ResponseEntity<?> deletePostById(Long id, Principal principal) {
        LOG.info("Deleting event post with ID: {}, user: {}", id, principal.getName());
        Optional<EventPost> post = repository.findById(id);
        if (post.isPresent()) {
            var eventPost = post.get();
            LOG.info("Event post with ID: {} found, event: {}", id, eventPost);
            if (eventPost.getPostedBy().equals(principal.getName())) {
                LOG.info("User authorized to delete event post with ID: {}, user: {}", id, principal.getName());
                repository.deleteById(id);
                return ResponseEntity.ok().build();
            } else {
                LOG.warn("User not authorized to delete event post with ID: {}, user: {}", id, principal.getName());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to delete this post.");
            }
        }
        LOG.warn("Event post with ID: {} not found", id);
        return ResponseEntity.notFound().build();
    }
}
