/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager;

import org.hibernate.dialect.PostgreSQLDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.nativex.hint.TypeHint;

/**
 * The EventManagerApplication class is the main class of the EventManager application.
 */
@SpringBootApplication
@TypeHint(types = PostgreSQLDialect.class, typeNames = "org.hibernate.dialect.PostgreSQLDialect")
public class EventManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventManagerApplication.class, args);
    }

}
