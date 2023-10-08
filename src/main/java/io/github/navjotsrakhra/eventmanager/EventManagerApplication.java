/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

/**
 * The EventManagerApplication class is the main class of the EventManager application.
 */
@SpringBootApplication
public class EventManagerApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("IST"));
        SpringApplication.run(EventManagerApplication.class, args);
    }

}
