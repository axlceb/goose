package org.axlceb.goose;


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
        return random.nextInt((max - min) + 1) + min;
    }
}
