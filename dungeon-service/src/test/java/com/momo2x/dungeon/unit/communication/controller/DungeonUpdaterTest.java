package com.momo2x.dungeon.unit.communication.controller;

import com.momo2x.dungeon.communication.controller.out.DungeonUpdater;
import com.momo2x.dungeon.communication.model.CoordinateDto;
import com.momo2x.dungeon.communication.model.WalkerDto;
import com.momo2x.dungeon.communication.model.WalkerMapper;
import com.momo2x.dungeon.communication.model.WalkerMapperImpl;
import com.momo2x.dungeon.engine.actors.DungeonAutonomousWalker;
import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static com.momo2x.dungeon.config.WebSocketConstants.SIMPLE_BROKER_DESTINATION;
import static com.momo2x.dungeon.unit.MapTestUtil.mockDungeonAutoWalker;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DungeonUpdaterTest {

    private final WalkerMapper mapper = new WalkerMapperImpl();

    @Mock
    private SimpMessagingTemplate messageTemplate;

    @Captor
    private ArgumentCaptor<String> destinationCaptor;

    @Captor
    private ArgumentCaptor<WalkerDto> walkerDtoCaptor;

    @Test
    void broadcast() {
        DungeonAutonomousWalker walker = mockDungeonAutoWalker.get();
        walker.setPreviousCell(new DungeonCell(new DungeonCoord(0, 0)));
        walker.setCell(new DungeonCell(new DungeonCoord(1, 1)));

        new DungeonUpdater(messageTemplate, mapper).broadcast(walker);

        verify(messageTemplate).convertAndSend(destinationCaptor.capture(), walkerDtoCaptor.capture());

        assertThat(
                SIMPLE_BROKER_DESTINATION,
                equalTo(destinationCaptor.getValue()));
        assertThat(
                new WalkerDto(
                        walker.getId(),
                        walker.getAvatar(),
                        new CoordinateDto(
                                walker.getPreviousCell().getCoord().x(),
                                walker.getPreviousCell().getCoord().y()),
                        new CoordinateDto(
                                walker.getCell().getCoord().x(),
                                walker.getCell().getCoord().y())),
                equalTo(walkerDtoCaptor.getValue()));
    }

}