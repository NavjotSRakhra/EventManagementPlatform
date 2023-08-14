/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved
 */

package io.github.navjotsrakhra.eventmanager.exception;

public class UserNameTakenException extends Exception {
    public UserNameTakenException() {
        super("Username already taken");
    }
}
