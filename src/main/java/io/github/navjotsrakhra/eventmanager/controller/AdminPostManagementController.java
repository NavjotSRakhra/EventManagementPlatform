/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.controller;

import io.github.navjotsrakhra.eventmanager.dataModel.dto.EventPostDTO;
import io.github.navjotsrakhra.eventmanager.exception.DateValidationFailedException;
import io.github.navjotsrakhra.eventmanager.service.EventPostEditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The AdminPostManagementController class handles HTTP requests related to event posts by admin users.
 */
@RestController
@RequestMapping("/admin/events")
public class AdminPostManagementController {

    private final EventPostEditService eventPostEditService;

    /**
     * Constructor for the AdminPostManagementController class.
     *
     * @param eventPostEditService Service for editing event posts.
     */
    public AdminPostManagementController(EventPostEditService eventPostEditService) {
        this.eventPostEditService = eventPostEditService;
    }

    /**
     * Handles DELETE requests for the "/admin/events/delete/{id}" URL to delete an event post.
     *
     * @param id The ID of the event post to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEventPost(@PathVariable Long id) {
        return eventPostEditService.deletePostById(id);
    }

    /**
     * Handles POST requests for the "/admin/events/edit/{id}" URL to edit an event post.
     *
     * @param id           The ID of the event post to edit.
     * @param eventPostDTO The updated event post data.
     * @return ResponseEntity indicating the result of the update operation.
     * @throws DateValidationFailedException Thrown if the event post's start date is after its end date.
     */
    @PostMapping("/edit/{id}")
    public ResponseEntity<?> editEventPost(@PathVariable Long id, @RequestBody EventPostDTO eventPostDTO) throws DateValidationFailedException {
        return eventPostEditService.updatePostById(id, eventPostDTO.toEventPost());
    }

    /**
     * Handles exceptions thrown by the {@link EventPostEditService} class.
     *
     * @param e The exception thrown.
     * @return ResponseEntity containing the exception message.
     */
    @ExceptionHandler(DateValidationFailedException.class)
    public ResponseEntity<?> handleDateValidationFailedException(DateValidationFailedException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
