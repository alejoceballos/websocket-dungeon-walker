package com.momo2x.dungeon.unit.engine;

import com.momo2x.dungeon.communication.controller.DungeonUpdater;
import com.momo2x.dungeon.engine.DungeonEngine;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.map.DungeonMap;
import com.momo2x.dungeon.engine.movement.SimpleBounceStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.momo2x.dungeon.unit.config.map.MapTestUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.*;

class DungeonEngineTest {

    private final DungeonMap mapMock = mock(DungeonMap.class);

    private final DungeonUpdater updaterMock = mock(DungeonUpdater.class);

    @BeforeEach
    void setUpMock() {
        when(this.mapMock.getMap()).thenReturn(MAP_CELLS);
        when(this.mapMock.getWalls()).thenReturn(MAP_WALLS);
        when(this.mapMock.getWalkers()).thenReturn(MAP_WALKERS);

        final var cell = MAP_CELLS.get(new DungeonCoord(2, 2));
        cell.setElement(DUNGEON_WALKER);
        DUNGEON_WALKER.setCell(cell);
    }

    @AfterEach
    void resetMock() {
        reset(this.mapMock, this.updaterMock);

        final var cell = MAP_CELLS.get(new DungeonCoord(2, 2));
        DUNGEON_WALKER.setCell(null);
    }

    @Test
    void init() {
        final var engine = new DungeonEngine(this.mapMock, this.updaterMock);
        engine.init();

        assertThat(engine.getManagers().size(), equalTo(1));

        final var manager = engine.getManagers().get(0);

        assertThat(manager.getWalker(), equalTo(DUNGEON_WALKER));
        assertThat(manager.getBounce(), instanceOf(SimpleBounceStrategy.class));
    }


}