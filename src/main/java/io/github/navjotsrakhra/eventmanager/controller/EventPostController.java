/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.controller;

import io.github.navjotsrakhra.eventmanager.dataModel.EventPost;
import io.github.navjotsrakhra.eventmanager.dataModel.exposed.EventPostRecord;
import io.github.navjotsrakhra.eventmanager.exception.DateValidationFailedException;
import io.github.navjotsrakhra.eventmanager.service.EventPostAddService;
import io.github.navjotsrakhra.eventmanager.service.EventPostGetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The EventPostController class handles HTTP requests related to event posts.
 */
@RestController
@RequestMapping("/events")
public class EventPostController {
    private final EventPostGetService eventPostGetService;
    private final EventPostAddService eventPostAddService;

    /**
     * Constructor for the EventPostController class.
     *
     * @param eventPostGetService Service for retrieving event posts.
     * @param eventPostAddService Service for adding event posts.
     */
    public EventPostController(EventPostGetService eventPostGetService, EventPostAddService eventPostAddService) {
        this.eventPostGetService = eventPostGetService;
        this.eventPostAddService = eventPostAddService;
    }

    /**
     * Handles GET requests for the "/events" URL and returns a list of all event posts.
     *
     * @return ResponseEntity containing a list of EventPostRecord objects.
     */
    @GetMapping
    public ResponseEntity<List<EventPostRecord>> getAllEvents() {
        return eventPostGetService.getAllPosts();
    }

    /**
     * Handles POST requests for the "/events/post" URL to add a new event post. {@link EventPostRecord} is the exposed version of {@link io.github.navjotsrakhra.eventmanager.dataModel.EventPost}
     *
     * @param newEvent The EventPostRecord object to be added, validated using @Valid annotation in the {@link EventPostAddService#addEvent(EventPost)}.
     * @return ResponseEntity indicating the result of the operation.
     * @throws DateValidationFailedException if date validation fails.
     */
    @PostMapping("/post")
    public ResponseEntity<?> addEvent(@RequestBody EventPostRecord newEvent) throws DateValidationFailedException {
        return eventPostAddService.addEvent(newEvent.toEventPost());
    }

    /**
     * Handles exceptions related to method argument validation and date validation.
     *
     * @param methodArgumentNotValidException Exception related to method argument validation.
     * @param dateValidationFailedException   Exception related to date validation.
     * @return ResponseEntity containing a list of error messages with a BAD_REQUEST status.
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, DateValidationFailedException.class})
    public ResponseEntity<List<String>> handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException, DateValidationFailedException dateValidationFailedException) {
        List<String> errors = new ArrayList<>();


        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error -> errors.add((error.getDefaultMessage())));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
