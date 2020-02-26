package org.axlceb.goose;

import java.util.Arrays;
import java.util.stream.Stream;


public enum Commands {
    ADD("add"),
    MOVE("move"),
    EXIT("exit"),
    DEFAULT("default");

    private final String text;

    Commands(String text) {
        this.text = text;
    }

    public static Commands fromString(String text) {
        var command = Stream.of(text.split(" ")).findFirst().orElse("default");
        return Arrays.stream(Commands.values()).filter(c -> c.text.equalsIgnoreCase(command))
                .findFirst()
                .orElse(DEFAULT);
    }
}
