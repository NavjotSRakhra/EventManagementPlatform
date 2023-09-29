/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.controller;

import io.github.navjotsrakhra.eventmanager.dataModel.dto.EventPostDTO;
import io.github.navjotsrakhra.eventmanager.logging.Logger;
import io.github.navjotsrakhra.eventmanager.service.EventPostGetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The EventPostController class handles HTTP requests related to event posts.
 */
@RestController
@RequestMapping("/events")
public class EventPostController {
    private final EventPostGetService eventPostGetService;

    /**
     * Constructor for the EventPostController class.
     *
     * @param eventPostGetService Service for retrieving event posts.
     */
    public EventPostController(EventPostGetService eventPostGetService) {
        this.eventPostGetService = eventPostGetService;
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
        Logger.LOG.info("Getting all events, pageable: {}", pagination);
        return eventPostGetService.getPostsWithPagination(pagination);
    }
}
