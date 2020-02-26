package org.axlceb.goose.board.space;

import java.util.function.Function;

public interface Space {

    String getName();

    Function<Integer, Integer> getSpaceRule();
}
