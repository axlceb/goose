package org.axlceb.goose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {
    private final String name;
    private Integer step = 0;

    public Player(String name) {
        this.name = name;
    }

    public static Player create(String name) {
        return new Player(name);
    }

    public void move(Integer steps) {
        this.step += steps;
    }
}
