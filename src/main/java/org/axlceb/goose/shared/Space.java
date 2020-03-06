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

    public static Space fromString(String text) {
        return Arrays.stream(Space.values()).filter(c -> c.text.equalsIgnoreCase(text))
                .findFirst()
                .orElse(DEFAULT);
    }
}
