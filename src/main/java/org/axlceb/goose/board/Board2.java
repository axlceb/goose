package org.axlceb.goose.board;

import org.axlceb.goose.GooseGame2;
import org.axlceb.goose.board.space.DefaultSpace;
import org.axlceb.goose.board.space.Space;
import org.axlceb.goose.board.space.TheBridgeSpace;
import org.axlceb.goose.board.space.TheGooseSpace;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board2 {

    private final List<Space> spaces;

    public Board2() {
        spaces = IntStream.rangeClosed(0, GooseGame2.FINAL_SPACE)
                .mapToObj(i -> {
                    switch (i) {
                        case 0:
                            return new DefaultSpace("Start", i);
                        case 6:
                            return new TheBridgeSpace("The Bridge", i);
                        case 5:
                        case 9:
                        case 14:
                        case 18:
                        case 23:
                        case 27:
                            return new TheGooseSpace(i + ", The Goose", i);
                        default:
                            return new DefaultSpace(Integer.toString(i), i);
                    }
                })
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    public Space getSpace(Integer index) {
        return spaces.get(index);
    }
}
