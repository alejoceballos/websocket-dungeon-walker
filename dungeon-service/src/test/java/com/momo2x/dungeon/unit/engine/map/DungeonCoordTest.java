package com.momo2x.dungeon.unit.engine.map;

import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.movement.DirectionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.momo2x.dungeon.engine.movement.DirectionType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class DungeonCoordTest {

    /*
           0     1     2
        +-----+-----+-----+
      0 | 0,0 | 1,0 | 2,0 |
        +-----+-----+-----+
      1 | 0,1 | 1,1 | 2,1 |
        +-----+-----+-----+
      2 | 0,2 | 1,2 | 2,2 |
        +-----+-----+-----+
     */

    private final DungeonCoord coord = new DungeonCoord(1, 1);

    private static Stream<Arguments> getCoordAtParameters() {
        return Stream.of(
                arguments(NE, new DungeonCoord(2, 0)),
                arguments(E, new DungeonCoord(2, 1)),
                arguments(SE, new DungeonCoord(2, 2)),
                arguments(S, new DungeonCoord(1, 2)),
                arguments(SW, new DungeonCoord(0, 2)),
                arguments(W, new DungeonCoord(0, 1)),
                arguments(NW, new DungeonCoord(0, 0)),
                arguments(N, new DungeonCoord(1, 0)));
    }

    private static Stream<Arguments> calculateDirectionParameters() {
        return Stream.of(
                arguments(new DungeonCoord(2, 0), NE),
                arguments(new DungeonCoord(2, 1), E),
                arguments(new DungeonCoord(2, 2), SE),
                arguments(new DungeonCoord(1, 2), S),
                arguments(new DungeonCoord(0, 2), SW),
                arguments(new DungeonCoord(0, 1), W),
                arguments(new DungeonCoord(0, 0), NW),
                arguments(new DungeonCoord(1, 0), N));
    }

    @ParameterizedTest
    @MethodSource("getCoordAtParameters")
    void getCoordAt(
            final DirectionType direction,
            final DungeonCoord expectedCoord) {
        assertThat(coord.getCoordAt(direction), equalTo(expectedCoord));
    }

    @ParameterizedTest
    @MethodSource("calculateDirectionParameters")
    void calculateDirection(
            final DungeonCoord coordTo,
            final DirectionType expectedDirection) {
        assertThat(coord.calculateDirection(coordTo), equalTo(expectedDirection));
    }

    @Test
    void testToString() {
        assertThat(new DungeonCoord(0, 0).toString(), equalTo("(0,0)"));
    }

}