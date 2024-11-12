/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.service;

import io.github.NavjotSRakhra.neuralNetwork.NeuralNetwork;
import io.github.navjotsrakhra.eventmanager.dataModel.dto.EventPostAdminDTO;
import io.github.navjotsrakhra.eventmanager.dataModel.dto.EventPostDTO;
import io.github.navjotsrakhra.eventmanager.repository.EventPostRepository;
import io.github.navjotsrakhra.eventmanager.user.authentication.data.model.UserObject;
import io.github.navjotsrakhra.eventmanager.user.authentication.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

/**
 * The EventPostGetService class provides methods for retrieving EventPost entities from the repository.
 */
@Service
public class EventPostGetService {
    private final EventPostRepository repository;
    private final UserRepository userRepository;
    private final NeuralNetwork neuralNetwork;

    /**
     * Constructor for the EventPostGetService class.
     *
     * @param repository The JpaRepository used for retrieving event posts.
     */
    public EventPostGetService(EventPostRepository repository, UserRepository userRepository, NeuralNetwork neuralNetwork) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.neuralNetwork = neuralNetwork;
    }

    /**
     * Get a list of all event posts.
     *
     * @return ResponseEntity containing a list of EventPostDTO objects.
     * @deprecated Use {@link #getPostsWithPagination(Pageable, Principal)} instead.
     */
    @Deprecated
    public ResponseEntity<List<EventPostDTO>> getAllPosts() {
        return ResponseEntity.ok(
                repository
                        .findAll()
                        .stream()
                        .map(
                                e -> new EventPostDTO(e.getId(), e.getTitle(), e.getContent(), e.getLocation(), e.getEnrollmentLink(), e.getImageLink(), e.getStartDay(), e.getEndDay(), e.getStartTime(), e.getEndTime()))
                        .toList()
        );
    }

    /**
     * Get a list of all event posts with pagination. See {@link Pageable}. Defaults to page 0, size 5, sorted by postedAt.
     *
     * @param pageable  The pagination object. See {@link Pageable}.
     * @param principal The user that maybe associated with events.
     * @return ResponseEntity containing a list of EventPostDTO objects.
     */
    public ResponseEntity<Page<EventPostDTO>> getPostsWithPagination(Pageable pageable, Principal principal) {
        if (principal != null) {
            UserObject userObject = userRepository.findByUsername(principal.getName());
            long suggestedEventId = Long.parseLong(String.valueOf(neuralNetwork.feedForward(new double[]{userObject.getId()})[0]));

            return ResponseEntity.ok(
                    repository
                            .findEventPostById(pageable, suggestedEventId)
                            .map(
                                    e -> new EventPostDTO(e.getId(), e.getTitle(), e.getContent(), e.getLocation(), e.getEnrollmentLink(), e.getImageLink(), e.getStartDay(), e.getEndDay(), e.getStartTime(), e.getEndTime()))
            );
        }
        return ResponseEntity.ok(
                repository
                        .findAll(pageable)
                        .map(
                                e -> new EventPostDTO(e.getId(), e.getTitle(), e.getContent(), e.getLocation(), e.getEnrollmentLink(), e.getImageLink(), e.getStartDay(), e.getEndDay(), e.getStartTime(), e.getEndTime()))
        );
    }

    /**
     * Get a list of all event posts with pagination posted by the specified user.
     * See {@link Pageable}. Defaults to page 0, size 5, sorted by postedAt.
     *
     * @param pageable  The pagination object. See {@link Pageable}.
     * @param principal The Principal object containing the username of the user making the request.
     * @return ResponseEntity containing a list of EventPostDTO objects.
     */
    public ResponseEntity<Page<EventPostDTO>> getPostsWithPaginationOfUser(Pageable pageable, Principal principal) {
        return ResponseEntity.ok(
                repository.findEventPostByPostedBy(principal.getName(), pageable)
                        .map(
                                e -> new EventPostDTO(e.getId(), e.getTitle(), e.getContent(), e.getLocation(), e.getEnrollmentLink(), e.getImageLink(), e.getStartDay(), e.getEndDay(), e.getStartTime(), e.getEndTime())
                        )
        );
    }

    /**
     * Get a list of all event posts with pagination. See {@link Pageable}.
     * Defaults to page 0, size 5, sorted by postedAt.
     * This method is intended for use by administrators.
     *
     * @param pageable The pagination object. See {@link Pageable}.
     * @return ResponseEntity containing a list of EventPostAdminDTO objects.
     */
    public ResponseEntity<Page<EventPostAdminDTO>> getAdminPostsWithPagination(Pageable pageable) {
        return ResponseEntity.ok(
                repository
                        .findAll(pageable)
                        .map(
                                e -> new EventPostAdminDTO(e.getId(), e.getTitle(), e.getContent(), e.getLocation(), e.getEnrollmentLink(), e.getImageLink(), e.getStartDay(), e.getEndDay(), e.getStartTime(), e.getEndTime(), e.getPostedBy(), e.getPostedAt()))
        );
    }
}
