/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The PageController class is responsible for handling HTTP requests related to page navigation.
 */
@Controller
public class PageController {
    /**
     * This method handles the GET request for the "/login" URL and returns the "login" view.
     *
     * @return A String representing the name of the view of Login page.
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    ;
}
