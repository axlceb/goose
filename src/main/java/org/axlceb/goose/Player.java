package org.axlceb.goose;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Player {

    private final String name;
    @EqualsAndHashCode.Exclude
    private Integer step = 0;

    public Player(String name) {
        this.name = name;
    }

    public static Player create(String name) {
        return new Player(name);
    }

    public void move(Integer steps) {
        System.out.print("The player " + name + " have moved from " + step);
        this.step += steps;
        System.out.print(" to " + step);
        System.out.println("  ");

    }
}
