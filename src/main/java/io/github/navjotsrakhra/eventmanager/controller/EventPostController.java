/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved
 */

package io.github.navjotsrakhra.eventmanager.controller;

import io.github.navjotsrakhra.eventmanager.dataModel.EventPost;
import io.github.navjotsrakhra.eventmanager.exception.DateValidationFailedException;
import io.github.navjotsrakhra.eventmanager.service.EventPostAddService;
import io.github.navjotsrakhra.eventmanager.service.EventPostGetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventPostController {
    private final EventPostGetService eventPostGetService;
    private final EventPostAddService eventPostAddService;

    public EventPostController(EventPostGetService eventPostGetService, EventPostAddService eventPostAddService) {
        this.eventPostGetService = eventPostGetService;
        this.eventPostAddService = eventPostAddService;
    }

    @GetMapping
    public ResponseEntity<List<EventPost>> getAllEvents() {
        return ResponseEntity.ok(eventPostGetService.getAllPosts());
    }

    @PostMapping("/post")
    public ResponseEntity<?> addEvent(@Valid @RequestBody EventPost newEvent) {
        return eventPostAddService.addEvent(newEvent);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, DateValidationFailedException.class})
    public ResponseEntity<List<String>> handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException, DateValidationFailedException dateValidationFailedException) {
        List<String> errors = new ArrayList<>();


        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error -> errors.add((error.getDefaultMessage())));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
