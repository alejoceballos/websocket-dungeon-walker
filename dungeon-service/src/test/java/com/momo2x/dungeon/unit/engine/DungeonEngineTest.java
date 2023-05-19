package com.momo2x.dungeon.unit.engine;

import com.momo2x.dungeon.communication.controller.out.DungeonUpdater;
import com.momo2x.dungeon.engine.DungeonEngine;
import com.momo2x.dungeon.engine.actors.DungeonAutonomousWalker;
import com.momo2x.dungeon.engine.interaction.bounce.SimpleBounceStrategy;
import com.momo2x.dungeon.engine.map.DungeonMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.momo2x.dungeon.unit.MapTestUtil.mockDungeonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

class DungeonEngineTest {

    private final DungeonUpdater updaterMock = mock(DungeonUpdater.class);

    private DungeonMap mapMock;

    private DungeonAutonomousWalker dungeonAutoWalkerMock;

    @BeforeEach
    void setUpMock() {
        this.mapMock = mockDungeonMap.get();
        this.dungeonAutoWalkerMock = (DungeonAutonomousWalker) this.mapMock
                .getWalkers()
                .values()
                .stream()
                .findFirst()
                .get();
    }

    @AfterEach
    void resetMock() {
        reset(this.updaterMock);
    }

    @Test
    void init() {
        final var engine = new DungeonEngine(this.mapMock, this.updaterMock);
        engine.init();

        assertThat(engine.getManagers().size(), equalTo(1));

        final var manager = engine.getManagers().get(0);

        assertThat(manager.getWalker(), equalTo(this.dungeonAutoWalkerMock));
        assertThat(manager.getBounce(), instanceOf(SimpleBounceStrategy.class));
    }


}