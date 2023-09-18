/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.dataModel.dto;

import io.github.navjotsrakhra.eventmanager.dataModel.EventPost;
import io.github.navjotsrakhra.eventmanager.exception.DateValidationFailedException;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The EventPostDTO class represents an exposed version of an EventPost, providing a simplified interface for creating event posts.
 */
public record EventPostDTO(Long ID, String title, String content, String location, LocalDate startDay,
                           LocalDate endDay,
                           LocalTime startTime, LocalTime endTime) {
    /**
     * Converts this EventPostRecord to an EventPost object.
     *
     * @return EventPost object created from the EventPostRecord.
     * @throws DateValidationFailedException if date validation fails.
     */
    public EventPost toEventPost() throws DateValidationFailedException {
        return new EventPost(title, content, location, startDay, endDay, startTime, endTime);
    }
}
