/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved
 */

package io.github.navjotsrakhra.eventmanager.service;

import io.github.navjotsrakhra.eventmanager.dataModel.EventPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventPostGetService {
    private final JpaRepository<EventPost, Long> repository;

    public EventPostGetService(JpaRepository<EventPost, Long> repository) {
        this.repository = repository;
    }

    public List<EventPost> getAllPosts() {
        return new ArrayList<>(repository.findAll());
    }
}
