/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved
 */

package io.github.navjotsrakhra.eventmanager.dataModel;

import io.github.navjotsrakhra.eventmanager.exception.DateValidationFailedException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class EventPost {
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

    public EventPost() {
    }

    public EventPost(String title, String content, String location, LocalDate startDay, LocalDate endDay, LocalTime startTime, LocalTime endTime) throws DateValidationFailedException {
        this.title = title;
        this.content = content;
        this.location = location;
        this.startDay = startDay;
        this.endDay = endDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getStartDay() {
        return startDay;
    }

    public void setStartDay(LocalDate startDay) throws DateValidationFailedException {
        if (endDay != null && startDay.isAfter(endDay))
            throw new DateValidationFailedException("Start date must be on or before end day");
        this.startDay = startDay;
    }

    public LocalDate getEndDay() {
        return endDay;
    }

    public void setEndDay(LocalDate endDay) throws DateValidationFailedException {
        if (startDay != null && startDay.isAfter(endDay))
            throw new DateValidationFailedException("Start date must be on or before end day");
        this.endDay = endDay;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) throws DateValidationFailedException {
        if (endTime != null && startTime.isAfter(endTime))
            throw new DateValidationFailedException("Start time must be on or before end time");
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) throws DateValidationFailedException {
        if (startTime != null && !startTime.isBefore(endTime))
            throw new DateValidationFailedException("Start time must be  before end time");
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
