/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.exception;

/**
 * The PostNotFoundException class is a custom exception class that is thrown when a post is not found.
 */
public class PostNotFoundException extends Exception {
    /**
     * Constructs a new PostNotFoundException with the specified detail message.
     */
    public PostNotFoundException() {
        super("Post not found");
    }
}
