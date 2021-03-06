package org.axlceb.goose.shared;

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

    /**
     * Not required just an example how to change the access modifier in constructor and use the Factory pattern. Player Interface is recommended
     * @param name name not null value is manage by the code orElse @NotNull annotation can be used
     */
    private Player(String name) {
        this.name = name;
    }

    public static Player create(String name) {
        return new Player(name);
    }

    public void move(Integer steps) {
        System.out.print("the player " + this.name + " move from " + this.step);
        this.step += steps;
        System.out.println(" to " + this.step);
    }

    @Override
    public String toString() {
        return this.name + ':' + this.step;
    }
}
