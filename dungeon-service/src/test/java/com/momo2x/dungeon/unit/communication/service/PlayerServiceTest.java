package com.momo2x.dungeon.unit.communication.service;

import com.momo2x.dungeon.communication.controller.out.DungeonUpdater;
import com.momo2x.dungeon.communication.model.WalkerMapper;
import com.momo2x.dungeon.communication.model.WalkerMapperImpl;
import com.momo2x.dungeon.communication.service.PlayerService;
import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.map.CellException;
import com.momo2x.dungeon.engine.map.DungeonMap;
import com.momo2x.dungeon.engine.movement.MovementException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static com.momo2x.dungeon.engine.movement.DirectionType.E;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ComponentScan(basePackageClasses = {WalkerMapperImpl.class})
class PlayerServiceTest {

    @Mock
    private DungeonMap map;

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @Mock
    private WalkerMapper mapper;

    @Spy
    @InjectMocks
    private DungeonUpdater updater;

    @Mock
    private DungeonWalker walker;

    @Captor
    private ArgumentCaptor<DungeonWalker> walkerCaptor;

    @BeforeEach
    void mockDependencies() throws CellException, MovementException {
        when(this.map.move("id", E)).thenReturn(this.walker);
        doNothing().when(this.updater).broadcast(any(DungeonWalker.class));
    }

    @AfterEach
    void resetMocks() {
        reset(
                this.map,
                this.simpMessagingTemplate,
                this.mapper,
                this.updater,
                this.walker);
    }

    @Test
    void move() throws CellException, MovementException {
        new PlayerService(this.map, this.updater).move("id", E);

        verify(this.updater).broadcast(this.walkerCaptor.capture());
        MatcherAssert.assertThat(this.walkerCaptor.getValue(), Matchers.equalTo(this.walker));

        verify(this.updater, times(1)).broadcast(this.walker);
    }

}