package org.axlceb.goose.exception;

public class PlayerNotFoundException extends RuntimeException {
    // NOTE: This is just an example of using custom Exception
    public PlayerNotFoundException(String name) {
        super("Player " + name + " not found");
    }
}
