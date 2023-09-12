/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.dataModel;

import io.github.navjotsrakhra.eventmanager.exception.DateValidationFailedException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * The EventPost class represents an event post entity with details such as title, content, location, and date/time information.
 */
@Getter
@Entity
public class EventPost {
    private final LocalDateTime postedAt;
    @Id
    @GeneratedValue
    private Long id;
    @NotNull(message = "Title is mandatory")
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotNull(message = "Content is mandatory")
    @NotBlank(message = "Content is mandatory")
    private String content;
    @NotNull(message = "Location is mandatory")
    @NotBlank(message = "Location is mandatory")
    private String location;
    @NotNull(message = "Start day is mandatory")
    private LocalDate startDay;
    @NotNull(message = "End day is mandatory")
    private LocalDate endDay;
    @NotNull(message = "Start time is mandatory")
    private LocalTime startTime;
    @NotNull(message = "End time is mandatory")
    private LocalTime endTime;

    /**
     * Default constructor initializes the 'postedAt' timestamp to the current date and time.
     */
    public EventPost() {
        this.postedAt = LocalDateTime.now();
    }

    /**
     * Constructor for creating an EventPost with provided details.
     *
     * @param title     The title of the event.
     * @param content   The content or description of the event.
     * @param location  The location where the event will take place.
     * @param startDay  The starting date of the event.
     * @param endDay    The ending date of the event.
     * @param startTime The starting time of the event.
     * @param endTime   The ending time of the event.
     * @throws DateValidationFailedException If date validation fails.
     */
    public EventPost(String title, String content, String location, LocalDate startDay, LocalDate endDay, LocalTime startTime, LocalTime endTime) throws DateValidationFailedException {
        this();

        setTitle(title);
        setContent(content);
        setLocation(location);

        setStartDay(startDay);
        setEndDay(endDay);
        setStartTime(startTime);
        setEndTime(endTime);
    }

    /**
     * Set the title of the event.
     *
     * @param title The title to be set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set the content or description of the event.
     *
     * @param content The content to be set.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Set the location where the event will take place.
     *
     * @param location The location to be set.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Set the starting date of the event, performing date validation.
     *
     * @param startDay The starting date to be set.
     * @throws DateValidationFailedException If date validation fails.
     */
    public void setStartDay(LocalDate startDay) throws DateValidationFailedException {

        // Ensure that the startDay is on or before the endDay
        if (endDay != null && startDay.isAfter(endDay))
            throw new DateValidationFailedException("Start date must be on or before end day");
        this.startDay = startDay;
    }

    /**
     * Set the ending date of the event, performing date validation.
     *
     * @param endDay The ending date to be set.
     * @throws DateValidationFailedException If date validation fails.
     */
    public void setEndDay(LocalDate endDay) throws DateValidationFailedException {

        // Ensure that the endDay is on or after the startDay
        if (startDay != null && startDay.isAfter(endDay))
            throw new DateValidationFailedException("Start date must be on or before end day");
        this.endDay = endDay;
    }

    /**
     * Set the starting time of the event, performing time validation.
     *
     * @param startTime The starting time to be set.
     * @throws DateValidationFailedException If time validation fails.
     */
    public void setStartTime(LocalTime startTime) throws DateValidationFailedException {

        // Ensure that for events on the same day, the startTime is before the endTime
        if (startDay != null && endDay != null && startDay.isEqual(endDay))
            if (endTime != null && startTime.isAfter(endTime))
                throw new DateValidationFailedException("Start time must be on or before end time");
        this.startTime = startTime;
    }

    /**
     * Set the ending time of the event, performing time validation.
     *
     * @param endTime The ending time to be set.
     * @throws DateValidationFailedException If time validation fails.
     */
    public void setEndTime(LocalTime endTime) throws DateValidationFailedException {

        // Ensure that for events on the same day, the startTime is before the endTime
        if (startDay != null && endDay != null && startDay.isEqual(endDay))
            if (startTime != null && !startTime.isBefore(endTime))
                throw new DateValidationFailedException("Start time must be  before end time");
        this.endTime = endTime;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
