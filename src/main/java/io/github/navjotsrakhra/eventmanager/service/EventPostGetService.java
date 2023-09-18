/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.service;

import io.github.navjotsrakhra.eventmanager.dataModel.EventPost;
import io.github.navjotsrakhra.eventmanager.dataModel.dto.EventPostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The EventPostGetService class provides methods for retrieving EventPost entities from the repository.
 */
@Service
public class EventPostGetService {
    private final JpaRepository<EventPost, Long> repository;

    /**
     * Constructor for the EventPostGetService class.
     *
     * @param repository The JpaRepository used for retrieving event posts.
     */
    public EventPostGetService(JpaRepository<EventPost, Long> repository) {
        this.repository = repository;
    }

    /**
     * Get a list of all event posts.
     *
     * @return ResponseEntity containing a list of EventPostDTO objects.
     * @deprecated Use {@link #getPostsWithPagination(Pageable)} instead.
     */
    @Deprecated
    public ResponseEntity<List<EventPostDTO>> getAllPosts() {
        return ResponseEntity.ok(
                repository
                        .findAll()
                        .stream()
                        .map(
                                e -> new EventPostDTO(e.getId(), e.getTitle(), e.getContent(), e.getLocation(), e.getStartDay(), e.getEndDay(), e.getStartTime(), e.getEndTime()))
                        .toList()
        );
    }

    /**
     * Get a list of all event posts with pagination. See {@link Pageable}. Defaults to page 0, size 5, sorted by postedAt.
     *
     * @param pageable The pagination object. See {@link Pageable}.
     * @return ResponseEntity containing a list of EventPostDTO objects.
     */
    public ResponseEntity<Page<EventPostDTO>> getPostsWithPagination(Pageable pageable) {
        return ResponseEntity.ok(
                repository
                        .findAll(pageable)
                        .map(
                                e -> new EventPostDTO(e.getId(), e.getTitle(), e.getContent(), e.getLocation(), e.getStartDay(), e.getEndDay(), e.getStartTime(), e.getEndTime()))
        );
    }
}
