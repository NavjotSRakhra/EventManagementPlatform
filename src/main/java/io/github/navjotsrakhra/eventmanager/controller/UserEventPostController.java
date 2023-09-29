/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.controller;

import com.fasterxml.jackson.core.JsonParseException;
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

import static io.github.navjotsrakhra.eventmanager.logging.Logger.LOG;

/**
 * The EventPostController class handles HTTP requests related to event posts.
 *
 * @author Navjot Singh Rakhra
 * @version 1.0
 */
@RestController
@RequestMapping("/user/events")
public class UserEventPostController {
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
    public UserEventPostController(EventPostGetService eventPostGetService, EventPostAddService eventPostAddService, EventPostEditService eventPostEditService) {
        this.eventPostGetService = eventPostGetService;
        this.eventPostAddService = eventPostAddService;
        this.eventPostEditService = eventPostEditService;
    }

    /**
     * Handles GET requests for the "/events" URL and retrieves a list of all events posted by the current user.
     * {@link EventPostDTO} is the exposed version of {@link io.github.navjotsrakhra.eventmanager.dataModel.EventPost}.
     *
     * @param pagination The pagination object. See {@link Pageable}. Defaults to page 0, size 5, sorted by postedAt.
     * @param principal  The Principal object is used to retrieve the username of the user making the request.
     * @return ResponseEntity containing a list of EventPostDTO objects representing events posted by current user.
     */
    @GetMapping
    public ResponseEntity<Page<EventPostDTO>> getAllEvents(@PageableDefault(size = 5, sort = "postedAt", direction = Sort.Direction.DESC) Pageable pagination, Principal principal) {
        LOG.info("Getting all events, pageable: {}, user updating: {}", pagination, principal.getName());
        return eventPostGetService.getPostsWithPaginationOfUser(pagination, principal);
    }

    /**
     * Handles POST requests for the "/events/post" URL to add a new event post if the user is the owner of the post.
     * {@link EventPostDTO} is the exposed version of {@link io.github.navjotsrakhra.eventmanager.dataModel.EventPost}
     *
     * @param newEvent  The EventPostDTO object to be added, validated using @Valid annotation in the {@link EventPostAddService#addEvent(EventPost, Principal)}.
     * @param principal The Principal object is used to retrieve the username of the user making the request.
     * @return ResponseEntity indicating the result of the operation.
     * @throws DateValidationFailedException if date validation fails.
     */
    @PostMapping("/post")
    public ResponseEntity<?> addEvent(@RequestBody EventPostDTO newEvent, Principal principal) throws DateValidationFailedException {
        LOG.info("Adding new event: {}, user adding: {}", newEvent, principal.getName());
        return eventPostAddService.addEvent(newEvent.toEventPost(), principal);
    }

    /**
     * Handles POST requests for the "/events/edit/{ID}" URL to edit a post if the user is the owner of the post.
     * {@link EventPostDTO} is the exposed version of {@link io.github.navjotsrakhra.eventmanager.dataModel.EventPost}
     *
     * @param editedEvent The EventPostDTO object to be edited, validated using @Valid annotation in the {@link EventPostAddService#addEvent(EventPost, Principal)}.
     * @param principal   The Principal object is used to retrieve the username of the user making the request.
     * @return ResponseEntity indicating the result of the operation.
     * @throws DateValidationFailedException if date validation fails.
     */
    @PostMapping("/edit/{ID}")
    public ResponseEntity<?> editEvent(@PathVariable Long ID, @RequestBody EventPostDTO editedEvent, Principal principal) throws DateValidationFailedException {
        LOG.info("Editing event with ID: {}, new values: {}, user editing: {}", ID, editedEvent, principal.getName());
        return eventPostEditService.updatePostById(ID, editedEvent.toEventPost(), principal);
    }

    /**
     * Handles DELETE requests for the "/events/delete/{ID}" URL to delete an event post if the user is the owner of the post.
     * {@link EventPostDTO} is the exposed version of {@link io.github.navjotsrakhra.eventmanager.dataModel.EventPost}
     *
     * @param ID        The ID of the event post to delete.
     * @param principal The Principal object is used to retrieve the username of the user making the request.
     * @return ResponseEntity indicating the result of the operation.
     */
    @DeleteMapping("/delete/{ID}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long ID, Principal principal) {
        LOG.info("Deleting event with ID: {}, user deleting: {}", ID, principal.getName());
        return eventPostEditService.deletePostById(ID, principal);
    }

    /**
     * Handles exceptions related to method argument validation.
     *
     * @param methodArgumentNotValidException Exception related to method argument validation.
     * @return ResponseEntity containing a list of error messages with a BAD_REQUEST status.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        LOG.error("Validation failed: {}", methodArgumentNotValidException.getMessage());
        LOG.trace(methodArgumentNotValidException.getMessage(), methodArgumentNotValidException);
        List<String> errors = new ArrayList<>();

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
        LOG.error("Date validation failed: {}", e.getMessage());
        LOG.trace(e.getMessage(), e);
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
        LOG.error("Post not found: {}", e.getMessage());
        LOG.trace(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    /**
     * Handles exceptions related to JSON parsing.
     *
     * @param e Exception related to JSON parsing.
     * @return ResponseEntity containing an error message with a BAD_REQUEST status.
     */
    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<String> handleJsonParseException(JsonParseException e) {
        LOG.error("Json parse exception: {}", e.getMessage());
        LOG.trace(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
