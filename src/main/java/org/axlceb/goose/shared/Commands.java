package org.axlceb.goose.shared;

import java.util.Arrays;
import java.util.stream.Stream;


public enum Commands {
    INFO("info"),
    ADD("add"),
    MOVE("move"),
    EXIT("exit"),
    DEFAULT("default");

    private final String text;

    Commands(String text) {
        this.text = text;
    }

    /**
     * Parse the text introduced by the user and return the proper enum Command. OrElse the default value
     * @param text by the user
     * @return proper enum Command
     */
    public static Commands fromString(String text) {
        var command = Stream.of(text.split(" ")).findFirst().orElse("default");
        return Arrays.stream(Commands.values()).filter(c -> c.text.equalsIgnoreCase(command))
                .findFirst()
                .orElse(DEFAULT);
    }
}
