package io.github.navjotsrakhra.eventmanager;

import io.github.navjotsrakhra.eventmanager.dataModel.EventPost;
import io.github.navjotsrakhra.eventmanager.service.EventPostSendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventPostController {
    private final EventPostSendService eventPostSendService;

    public EventPostController(EventPostSendService eventPostSendService) {
        this.eventPostSendService = eventPostSendService;
    }

    @GetMapping
    public ResponseEntity<List<EventPost>> getAllEvents() {
        return ResponseEntity.ok(eventPostSendService.getAllPosts());
    }
}
