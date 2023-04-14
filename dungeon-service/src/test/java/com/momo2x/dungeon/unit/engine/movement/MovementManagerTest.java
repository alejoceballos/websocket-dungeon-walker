package com.momo2x.dungeon.unit.engine.movement;

import com.momo2x.dungeon.engine.actors.DungeonAutonomousWalker;
import com.momo2x.dungeon.engine.map.CellException;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.movement.MovementException;
import com.momo2x.dungeon.engine.movement.MovementManager;
import com.momo2x.dungeon.engine.movement.SimpleBounceStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.momo2x.dungeon.engine.movement.DirectionType.NE;
import static com.momo2x.dungeon.engine.movement.DirectionType.SE;
import static com.momo2x.dungeon.unit.MapTestUtil.mockDungeonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

class MovementManagerTest {

    private final DungeonAutonomousWalker walker = mock(DungeonAutonomousWalker.class);

    private static Stream<Object> calculateSleepTmeParameters() {
        return Stream.of(
                Arguments.arguments(0, 1000),
                Arguments.arguments(1, 1000),
                Arguments.arguments(2, 900),
                Arguments.arguments(3, 800),
                Arguments.arguments(4, 700),
                Arguments.arguments(5, 600),
                Arguments.arguments(6, 500),
                Arguments.arguments(7, 400),
                Arguments.arguments(8, 300),
                Arguments.arguments(9, 200),
                Arguments.arguments(10, 100),
                Arguments.arguments(11, 100));
    }

    @AfterEach
    void resetMock() {
        reset(this.walker);
    }

    @ParameterizedTest
    @MethodSource("calculateSleepTmeParameters")
    void calculateSleepTme(int speed, long sleepTime) {
        when(this.walker.getSpeed()).thenReturn(speed);

        assertThat(
                new MovementManager(null, this.walker, null).calculateSleepTme(),
                equalTo(sleepTime));
    }

    @Test
    void move() throws CellException, MovementException {
        final var map = mockDungeonMap.get();
        final var walker = (DungeonAutonomousWalker) map.getWalkers().get("001");
        final var bounce = new SimpleBounceStrategy(map, walker);
        final var manager = new MovementManager(map, walker, bounce);

        manager.move();
        assertThat(walker.getDirection(), equalTo(NE));
        assertThat(walker.getCoord(), equalTo(new DungeonCoord(3, 1)));

        manager.move();
        assertThat(walker.getDirection(), equalTo(SE));
        assertThat(walker.getCoord(), equalTo(new DungeonCoord(3, 1)));

        manager.move();
        assertThat(walker.getDirection(), equalTo(SE));
        assertThat(walker.getCoord(), equalTo(new DungeonCoord(4, 2)));
    }
}