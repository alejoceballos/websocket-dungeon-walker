package com.momo2x.dungeon.unit.engine.movement;

import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.movement.MovementManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MovementManagerTest {

    private final DungeonWalker walker = mock(DungeonWalker.class);

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
        Mockito.reset(walker);
    }

    @ParameterizedTest
    @MethodSource("calculateSleepTmeParameters")
    void calculateSleepTme(int speed, long sleepTime) {
        when(this.walker.getSpeed()).thenReturn(speed);

        assertThat(
                new MovementManager(null, walker, null).calculateSleepTme(),
                equalTo(sleepTime));
    }
}