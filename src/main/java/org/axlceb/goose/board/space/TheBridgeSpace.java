package org.axlceb.goose.board.space;

import java.util.function.Function;

public class TheBridgeSpace extends DefaultSpace {

    public TheBridgeSpace(String name, Integer index) {
        super(name, index);
    }

    @Override
    public Function<Integer, Integer> getSpaceRule() {
        return (roll) -> 12;
    }
}
