/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved
 */

package io.github.navjotsrakhra.eventmanager.mock;

import io.github.navjotsrakhra.eventmanager.repository.EventPostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataAdder implements CommandLineRunner {
    private final EventPostRepository repository;

    public DataAdder(EventPostRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 10; i++) {
            i++;
//            repository.save(new EventPost("test", "test " + i, "tt " + i, LocalDate.now(), LocalDate.now(), LocalTime.of(10, 10), LocalTime.of(10, 10)));
            i--;
        }
    }
}
