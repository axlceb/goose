package org.axlceb.goose.exception;

public class CommandNotFoundException extends Exception {

    public CommandNotFoundException(String userString) {
        super("Command " + userString + " not found");
    }
}
