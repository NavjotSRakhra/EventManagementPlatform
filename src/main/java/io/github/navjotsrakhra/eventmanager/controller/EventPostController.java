/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.controller;

import io.github.navjotsrakhra.eventmanager.dataModel.EventPost;
import io.github.navjotsrakhra.eventmanager.dataModel.dto.EventPostDTO;
import io.github.navjotsrakhra.eventmanager.exception.DateValidationFailedException;
import io.github.navjotsrakhra.eventmanager.exception.PostNotFoundException;
import io.github.navjotsrakhra.eventmanager.service.EventPostAddService;
import io.github.navjotsrakhra.eventmanager.service.EventPostEditService;
import io.github.navjotsrakhra.eventmanager.service.EventPostGetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * The EventPostController class handles HTTP requests related to event posts.
 *
 * @author Navjot Singh Rakhra
 * @version 1.0
 */
@RestController
@RequestMapping("/events")
public class EventPostController {
    private final EventPostGetService eventPostGetService;
    private final EventPostAddService eventPostAddService;
    private final EventPostEditService eventPostEditService;

    /**
     * Constructor for the EventPostController class.
     *
     * @param eventPostGetService  Service for retrieving event posts.
     * @param eventPostAddService  Service for adding event posts.
     * @param eventPostEditService Service of edition event posts.
     */
    public EventPostController(EventPostGetService eventPostGetService, EventPostAddService eventPostAddService, EventPostEditService eventPostEditService) {
        this.eventPostGetService = eventPostGetService;
        this.eventPostAddService = eventPostAddService;
        this.eventPostEditService = eventPostEditService;
    }

    /**
     * Handles GET requests for the "/events" URL and retrieves a list of all event posts.
     * {@link EventPostDTO} is the exposed version of {@link io.github.navjotsrakhra.eventmanager.dataModel.EventPost}.
     *
     * @param pagination The pagination object. See {@link Pageable}. Defaults to page 0, size 5, sorted by postedAt.
     * @return ResponseEntity containing a list of EventPostDTO objects.
     */
    @GetMapping
    public ResponseEntity<Page<EventPostDTO>> getAllEvents(@PageableDefault(size = 5, sort = "postedAt", direction = Sort.Direction.DESC) Pageable pagination) {
        return eventPostGetService.getPostsWithPagination(pagination);
    }

    /**
     * Handles POST requests for the "/events/post" URL to add a new event post.
     * {@link EventPostDTO} is the exposed version of {@link io.github.navjotsrakhra.eventmanager.dataModel.EventPost}
     *
     * @param newEvent  The EventPostDTO object to be added, validated using @Valid annotation in the {@link EventPostAddService#addEvent(EventPost, Principal)}.
     * @param principal The Principal object is used to retrieve the username of the user making the request.
     * @return ResponseEntity indicating the result of the operation.
     * @throws DateValidationFailedException if date validation fails.
     */
    @PostMapping("/post")
    public ResponseEntity<?> addEvent(@RequestBody EventPostDTO newEvent, Principal principal) throws DateValidationFailedException {
        return eventPostAddService.addEvent(newEvent.toEventPost(), principal);
    }

    /**
     * Handles POST requests for the "/events/edit" URL to add a new event post. {@link EventPostDTO} is the exposed version of {@link io.github.navjotsrakhra.eventmanager.dataModel.EventPost}
     *
     * @param editedEvent The EventPostDTO object to be edited, validated using @Valid annotation in the {@link EventPostAddService#addEvent(EventPost, Principal)}.
     * @return ResponseEntity indicating the result of the operation.
     * @throws DateValidationFailedException if date validation fails.
     * @throws PostNotFoundException         if Post with given id is not found in the database.
     */
    @PostMapping("/edit/{ID}")
    public ResponseEntity<?> editEvent(@PathVariable Long ID, @RequestBody EventPostDTO editedEvent) throws PostNotFoundException, DateValidationFailedException {
        return eventPostEditService.updatePostById(ID, editedEvent.toEventPost());
    }

    /**
     * Handles DELETE requests for the "/events/delete/{ID}" URL to delete an event post.
     *
     * @param ID The ID of the event post to delete.
     * @return ResponseEntity indicating the result of the operation.
     */
    @DeleteMapping("/delete/{ID}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long ID) {
        return eventPostEditService.deletePostById(ID);
    }

    /**
     * Handles exceptions related to method argument validation.
     *
     * @param methodArgumentNotValidException Exception related to method argument validation.
     * @return ResponseEntity containing a list of error messages with a BAD_REQUEST status.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<String> errors = new ArrayList<>();

        if (methodArgumentNotValidException != null)
            methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error -> errors.add((error.getDefaultMessage())));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Handles exceptions related to method date validation.
     *
     * @param e Exception related to date validation.
     * @return ResponseEntity containing an error message with a BAD_REQUEST status.
     */
    @ExceptionHandler(DateValidationFailedException.class)
    public ResponseEntity<String> handleDateValidationException(DateValidationFailedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }


    /**
     * Handles exceptions related to post not found.
     *
     * @param e Exception related to error where the post to edit's ID is not found.
     * @return ResponseEntity containing an error message with a NOT_FOUND status.
     */
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<String> handlePostNotFoundException(PostNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
