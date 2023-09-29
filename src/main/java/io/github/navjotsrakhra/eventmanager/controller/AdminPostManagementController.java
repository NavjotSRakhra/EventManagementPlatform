/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.controller;

import io.github.navjotsrakhra.eventmanager.dataModel.dto.EventPostAdminDTO;
import io.github.navjotsrakhra.eventmanager.dataModel.dto.EventPostDTO;
import io.github.navjotsrakhra.eventmanager.exception.DateValidationFailedException;
import io.github.navjotsrakhra.eventmanager.service.EventPostEditService;
import io.github.navjotsrakhra.eventmanager.service.EventPostGetService;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * The AdminPostManagementController class handles HTTP requests related to event posts by admin users.
 */
@RestController
@RequestMapping("/admin/events")
public class AdminPostManagementController {

    private final EventPostEditService eventPostEditService;
    private final EventPostGetService eventPostGetService;

    private final Logger LOG = org.slf4j.LoggerFactory.getLogger(AdminPostManagementController.class);

    /**
     * Constructor for the AdminPostManagementController class.
     *
     * @param eventPostEditService Service for editing event posts.
     */
    public AdminPostManagementController(EventPostEditService eventPostEditService, EventPostGetService eventPostGetService) {
        this.eventPostEditService = eventPostEditService;
        this.eventPostGetService = eventPostGetService;
    }

    @GetMapping
    public ResponseEntity<Page<EventPostAdminDTO>> getAllEvents(@PageableDefault(size = 5, sort = "postedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        LOG.info("Getting all events, pageable: {}", pageable);
        return eventPostGetService.getAllPostsWithPagination(pageable);
    }

    /**
     * Handles DELETE requests for the "/admin/events/delete/{id}" URL to delete an event post.
     *
     * @param id The ID of the event post to delete.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEventPost(@PathVariable Long id) {
        LOG.info("Deleting event post with ID: {}", id);
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
        LOG.info("Updating event post with ID: {}, new values: {}", id, eventPostDTO);
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
        LOG.error("Date validation failed: {}", e.getMessage());
        LOG.trace(e.getMessage(), e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
