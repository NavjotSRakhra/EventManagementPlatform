/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.exception;

/**
 * The DateValidationFailedException is an exception class used to indicate that a date validation has failed.
 */
public class DateValidationFailedException extends Exception {

    /**
     * Constructs a new DateValidationFailedException with the specified detail message.
     *
     * @param message The detail message explaining the reason for the date validation failure.
     */
    public DateValidationFailedException(String message) {
        super(message);
    }
}
