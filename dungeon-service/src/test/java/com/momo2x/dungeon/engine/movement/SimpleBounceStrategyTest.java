package com.momo2x.dungeon.engine.movement;

import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.actors.DungeonWall;
import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.map.DungeonMap;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.momo2x.dungeon.engine.movement.DungeonDirectionType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleBounceStrategyTest {

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

    private static final DungeonCoord NE_COORD = new DungeonCoord(2, 0);
    private static final DungeonCoord SE_COORD = new DungeonCoord(2, 2);
    private static final DungeonCoord SW_COORD = new DungeonCoord(0, 2);
    private static final DungeonCoord NW_COORD = new DungeonCoord(0, 0);
    private static final DungeonCoord N_COORD = new DungeonCoord(1, 0);
    private static final DungeonCoord S_COORD = new DungeonCoord(1, 2);
    private static final DungeonCoord E_COORD = new DungeonCoord(2, 1);
    private static final DungeonCoord W_COORD = new DungeonCoord(0, 1);

    private static final DungeonCoord[] SOUTH_WALL = new DungeonCoord[]{SW_COORD, S_COORD, SE_COORD};
    private static final DungeonCoord[] EAST_WALL = new DungeonCoord[]{NE_COORD, E_COORD, SE_COORD};
    private static final DungeonCoord[] NORTH_WALL = new DungeonCoord[]{NW_COORD, N_COORD, NE_COORD};
    private static final DungeonCoord[] WEST_WALL = new DungeonCoord[]{NW_COORD, W_COORD, SW_COORD};
    private static final DungeonCoord[] SOUTH_AND_EAST_WALL = new DungeonCoord[]{SW_COORD, S_COORD, SE_COORD, E_COORD, NE_COORD};
    private static final DungeonCoord[] SOUTH_AND_WEST_WALL = new DungeonCoord[]{SW_COORD, S_COORD, SE_COORD, W_COORD, NW_COORD};
    private static final DungeonCoord[] NORTH_AND_EAST_WALL = new DungeonCoord[]{NW_COORD, N_COORD, NE_COORD, E_COORD, SE_COORD};
    private static final DungeonCoord[] NORTH_AND_WEST_WALL = new DungeonCoord[]{NW_COORD, N_COORD, NE_COORD, W_COORD, SW_COORD};

    private static Stream<Arguments> bounceStrategyParameters() {
        return Stream.of(
                Arguments.arguments(S, N_COORD, N, SOUTH_WALL),
                Arguments.arguments(E, W_COORD, W, EAST_WALL),
                Arguments.arguments(N, S_COORD, S, NORTH_WALL),
                Arguments.arguments(W, E_COORD, E, WEST_WALL),
                Arguments.arguments(SE, NE_COORD, NE, SOUTH_WALL),
                Arguments.arguments(SE, SW_COORD, SW, EAST_WALL),
                Arguments.arguments(SW, SE_COORD, SE, WEST_WALL),
                Arguments.arguments(SW, NW_COORD, NW, SOUTH_WALL),
                Arguments.arguments(NE, NW_COORD, NW, EAST_WALL),
                Arguments.arguments(NE, SE_COORD, SE, NORTH_WALL),
                Arguments.arguments(NW, NE_COORD, NE, WEST_WALL),
                Arguments.arguments(NW, SW_COORD, SW, NORTH_WALL),
                Arguments.arguments(SE, NW_COORD, NW, SOUTH_AND_EAST_WALL),
                Arguments.arguments(SW, NE_COORD, NE, SOUTH_AND_WEST_WALL),
                Arguments.arguments(NW, SE_COORD, SE, NORTH_AND_WEST_WALL),
                Arguments.arguments(NE, SW_COORD, SW, NORTH_AND_EAST_WALL));
    }

    @ParameterizedTest
    @MethodSource("bounceStrategyParameters")
    void bounce(
            final DungeonDirectionType direction,
            final DungeonCoord bounceCoord,
            final DungeonDirectionType bounceDirection,
            final DungeonCoord[] wallCoords) {
        final var strategy = createBounceStrategy(direction, bounceCoord, wallCoords);
        assertThat(strategy.bounceDirection(), equalTo(bounceDirection));
    }

    private BounceStrategy createBounceStrategy(
            DungeonDirectionType direction,
            DungeonCoord bounceTo,
            DungeonCoord... wallCoords
    ) {
        final var walker = mock(DungeonWalker.class);

        when(walker.getDirection()).thenReturn(direction);
        when(walker.getCoord()).thenReturn(new DungeonCoord(1, 1));

        final var map = mock(DungeonMap.class);

        final var resultCell = new DungeonCell(bounceTo);
        when(map.getCell(bounceTo)).thenReturn(resultCell);

        for (var wallCoord : wallCoords) {
            when(map.getCell(eq(wallCoord))).thenReturn(getWallCell(wallCoord));
        }

        return new SimpleBounceStrategy(map, walker);
    }

    private DungeonCell getWallCell(final DungeonCoord coord) {
        return DungeonCell.builder()
                .coord(coord)
                .element(new DungeonWall("", true))
                .build();
    }

}