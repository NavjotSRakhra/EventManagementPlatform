package io.github.navjotsrakhra.eventmanager.controller;

import io.github.navjotsrakhra.eventmanager.dataModel.EventPost;
import io.github.navjotsrakhra.eventmanager.service.EventPostAddService;
import io.github.navjotsrakhra.eventmanager.service.EventPostGetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

    @PostMapping
    public ResponseEntity<?> addEvent(@Valid @RequestBody EventPost newEvent) {
        return eventPostAddService.addEvent(newEvent);
    }
}
