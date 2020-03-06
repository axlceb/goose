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

    public Integer roll() {
        var result = random.nextInt((max - min) + 1) + min;
        System.out.print(" dice rolls " + result);
        return result;
    }
}
