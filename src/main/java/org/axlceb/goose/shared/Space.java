package org.axlceb.goose.shared;

import java.util.Arrays;

public enum Space {
    START("Start"),
    BRIDGE("Bridge"),
    GOOSE("Goose"),
    FINISH("Finish"),
    DEFAULT("default");

    private final String text;

    Space(String text) {
            this.text = text;
        }

    /**
     * Parse the text from the resources yml game config file to proper enum Space. OrElse the default value
     * @param text text from yml file
     * @return Space enum
     */
    public static Space fromString(String text) {
        return Arrays.stream(Space.values()).filter(c -> c.text.equalsIgnoreCase(text))
                .findFirst()
                .orElse(DEFAULT);
    }
}
