package com.momo2x.dungeon.unit.engine.map;

import com.momo2x.dungeon.engine.actors.DungeonAutonomousWalker;
import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.actors.ElementException;
import com.momo2x.dungeon.engine.map.CellException;
import com.momo2x.dungeon.engine.map.CoordinateException;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.map.DungeonMap;
import com.momo2x.dungeon.engine.movement.MovementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.momo2x.dungeon.engine.movement.DirectionType.E;
import static com.momo2x.dungeon.unit.MapTestUtil.mockDungeonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;

class DungeonMapTest {

    private DungeonMap map;

    @BeforeEach
    void startMocks() {
        this.map = mockDungeonMap.get();
    }

    @Test
    void getCellAt() {
        final var cell = this.map.getCellAt(new DungeonCoord(2, 2));
        assertThat(cell, notNullValue());
        assertThat(cell.getTopElement(), instanceOf(DungeonAutonomousWalker.class));
    }

    @Test
    void placeAndMoveElement() throws ElementException, CellException, CoordinateException, MovementException {
        final var cell = this.map.getCellAt(new DungeonCoord(2, 1));
        assertThat(cell.getTopElement().getId(), equalTo("D01"));

        final var walker = new DungeonWalker("id", "avatar", true, E);
        this.map.placeElement(walker, new DungeonCoord(2, 1));

        assertThat(cell.getTopElement(), equalTo(walker));

        final var nextCell = this.map.getCellAt(new DungeonCoord(3, 1));
        assertThat(nextCell.getTopElement().getId(), equalTo("G01"));

        this.map.move(walker);

        assertThat(cell.getTopElement().getId(), equalTo("D01"));
        assertThat(nextCell.getTopElement(), equalTo(walker));
    }

    @Test
    void placeAndMoveToWall() throws ElementException, CellException, CoordinateException, MovementException {
        final var cell = this.map.getCellAt(new DungeonCoord(4, 1));

        final var walker = new DungeonWalker("id", "avatar", true, E);
        this.map.placeElement(walker, new DungeonCoord(4, 1));

        final var nextCell = this.map.getCellAt(new DungeonCoord(5, 1));
        assertThat(nextCell.getTopElement().getId(), startsWith("W"));

        this.map.move(walker);

        assertThat(cell.getTopElement(), equalTo(walker));
        assertThat(nextCell.getTopElement().getId(), startsWith("W"));
    }

}