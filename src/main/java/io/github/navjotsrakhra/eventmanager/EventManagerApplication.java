/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

/**
 * The EventManagerApplication class is the main class of the EventManager application.
 */
@SpringBootApplication
@ImportRuntimeHints(value = io.github.navjotsrakhra.eventmanager.config.NativeImageRuntimeHintConfig.class)
public class EventManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventManagerApplication.class, args);
    }

}
