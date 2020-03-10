package org.axlceb.goose.shared;


import lombok.*;

import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dice {

    private Random random = new Random();

    private Integer min;
    private Integer max;

    @Setter(AccessLevel.NONE)
    private Integer result;

    public Integer roll() {
        this.result = random.nextInt((max - min) + 1) + min;
        System.out.print("Dice rolls " + this.result + ", ");
        return this.result;
    }
}
