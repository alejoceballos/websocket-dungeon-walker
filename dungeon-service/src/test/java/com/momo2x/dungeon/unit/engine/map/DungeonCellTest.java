package com.momo2x.dungeon.unit.engine.map;

import com.momo2x.dungeon.engine.actors.DungeonElement;
import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DungeonCellTest {

    private static Stream<Arguments> isBlockedProperties() {
        final var elementBlocked = mock(DungeonElement.class);
        when(elementBlocked.isBlocker()).thenReturn(true);

        final var elementNotBlocked = mock(DungeonElement.class);
        when(elementNotBlocked.isBlocker()).thenReturn(false);

        return Stream.of(
                arguments(new DungeonCell(null, elementBlocked), true),
                arguments(new DungeonCell(null, elementNotBlocked), false),
                arguments(new DungeonCell(null), false)
        );
    }

    private static Stream<Arguments> testToStringProperties() {
        final var expectedResult = "Cell (%s): %s";
        final var expectedNoCoord = "?,?";
        final var expectedCoord = "0,0";
        final var expectedNoElem = "empty";
        final var expectedElemId = "ID";

        final var coord = new DungeonCoord(0, 0);

        final var element = mock(DungeonElement.class);
        when(element.getId()).thenReturn(expectedElemId);

        return Stream.of(
                arguments(
                        new DungeonCell(null),
                        expectedResult.formatted(expectedNoCoord, expectedNoElem)),
                arguments(
                        new DungeonCell(coord),
                        expectedResult.formatted(expectedCoord, expectedNoElem)),
                arguments(
                        new DungeonCell(null, element),
                        expectedResult.formatted(expectedNoCoord, expectedElemId)),
                arguments(
                        new DungeonCell(coord, element),
                        expectedResult.formatted(expectedCoord, expectedElemId))
        );
    }

    @ParameterizedTest
    @MethodSource("isBlockedProperties")
    void isBlocked(final DungeonCell cell, final boolean expected) {
        assertThat(cell.isBlocked(), equalTo(expected));
    }

    @ParameterizedTest
    @MethodSource("testToStringProperties")
    void testToString(final DungeonCell cell, final String expected) {
        assertThat(cell.toString(), equalTo(expected));
    }
}