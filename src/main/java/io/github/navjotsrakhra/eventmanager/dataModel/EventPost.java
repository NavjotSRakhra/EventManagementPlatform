package io.github.navjotsrakhra.eventmanager.dataModel;

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
    private LocalDate eventStartDay;
    @NotNull(message = "End day is mandatory")
    private LocalDate eventEndDay;
    @NotNull(message = "Start time is mandatory")
    private LocalTime startTime;
    @NotNull(message = "End time is mandatory")
    private LocalTime endTime;

    public EventPost() {
    }

    public EventPost(String title, String content, String location, LocalDate eventStartDay, LocalDate eventEndDay, LocalTime startTime, LocalTime endTime) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.eventStartDay = eventStartDay;
        this.eventEndDay = eventEndDay;
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

    public LocalDate getEventStartDay() {
        return eventStartDay;
    }

    public void setEventStartDay(LocalDate eventStartDay) {
        this.eventStartDay = eventStartDay;
    }

    public LocalDate getEventEndDay() {
        return eventEndDay;
    }

    public void setEventEndDay(LocalDate eventEndDay) {
        this.eventEndDay = eventEndDay;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
