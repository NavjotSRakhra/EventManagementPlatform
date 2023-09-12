/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.exception;

public class PostNotFoundException extends Exception {
    public PostNotFoundException() {
        super("Post not found");
    }
}
