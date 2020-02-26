package org.axlceb.goose.exception;

public class PlayerAlreadyExistsException extends Exception {
    public PlayerAlreadyExistsException(String name) {
        super("Player " + name + " already exist");
    }
}
