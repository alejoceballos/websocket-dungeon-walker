package com.momo2x.dungeon.unit.engine.movement;

import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.actors.DungeonWall;
import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.map.DungeonMap;
import com.momo2x.dungeon.engine.movement.BounceStrategy;
import com.momo2x.dungeon.engine.movement.DirectionType;
import com.momo2x.dungeon.engine.movement.SimpleBounceStrategy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.momo2x.dungeon.engine.movement.DirectionType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.params.provider.Arguments.arguments;
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
                arguments(S, N_COORD, N, SOUTH_WALL),
                arguments(E, W_COORD, W, EAST_WALL),
                arguments(N, S_COORD, S, NORTH_WALL),
                arguments(W, E_COORD, E, WEST_WALL),
                arguments(SE, NE_COORD, NE, SOUTH_WALL),
                arguments(SE, SW_COORD, SW, EAST_WALL),
                arguments(SW, SE_COORD, SE, WEST_WALL),
                arguments(SW, NW_COORD, NW, SOUTH_WALL),
                arguments(NE, NW_COORD, NW, EAST_WALL),
                arguments(NE, SE_COORD, SE, NORTH_WALL),
                arguments(NW, NE_COORD, NE, WEST_WALL),
                arguments(NW, SW_COORD, SW, NORTH_WALL),
                arguments(SE, NW_COORD, NW, SOUTH_AND_EAST_WALL),
                arguments(SW, NE_COORD, NE, SOUTH_AND_WEST_WALL),
                arguments(NW, SE_COORD, SE, NORTH_AND_WEST_WALL),
                arguments(NE, SW_COORD, SW, NORTH_AND_EAST_WALL));
    }

    @ParameterizedTest
    @MethodSource("bounceStrategyParameters")
    void bounce(
            final DirectionType direction,
            final DungeonCoord bounceCoord,
            final DirectionType bounceDirection,
            final DungeonCoord[] wallCoords) {
        final var strategy = createBounceStrategy(direction, bounceCoord, wallCoords);
        assertThat(strategy.bounceDirection(), equalTo(bounceDirection));
    }

    private BounceStrategy createBounceStrategy(
            DirectionType direction,
            DungeonCoord bounceTo,
            DungeonCoord... wallCoords
    ) {
        final var walker = mock(DungeonWalker.class);

        when(walker.getDirection()).thenReturn(direction);
        when(walker.getCoord()).thenReturn(new DungeonCoord(1, 1));

        final var map = mock(DungeonMap.class);

        final var resultCell = new DungeonCell(bounceTo);
        when(map.getCellAt(bounceTo)).thenReturn(resultCell);

        for (var wallCoord : wallCoords) {
            when(map.getCellAt(eq(wallCoord))).thenReturn(getWallCell(wallCoord));
        }

        return new SimpleBounceStrategy(map, walker);
    }

    private DungeonCell getWallCell(final DungeonCoord coord) {
        return DungeonCell.builder()
                .coord(coord)
                .element(new DungeonWall("", "", true))
                .build();
    }

}