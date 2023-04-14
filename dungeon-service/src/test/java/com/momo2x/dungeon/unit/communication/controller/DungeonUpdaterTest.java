package com.momo2x.dungeon.unit.communication.controller;

import com.momo2x.dungeon.communication.controller.out.DungeonUpdater;
import com.momo2x.dungeon.communication.model.CoordinateDto;
import com.momo2x.dungeon.communication.model.ElementDto;
import com.momo2x.dungeon.communication.model.ElementMapper;
import com.momo2x.dungeon.communication.model.ElementMapperImpl;
import com.momo2x.dungeon.communication.model.MapUpdateDto;
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
import static com.momo2x.dungeon.unit.MapTestUtil.mockDungeonGrass;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DungeonUpdaterTest {

    private final ElementMapper mapper = new ElementMapperImpl();

    @Mock
    private SimpMessagingTemplate messageTemplate;

    @Captor
    private ArgumentCaptor<String> destinationCaptor;

    @Captor
    private ArgumentCaptor<MapUpdateDto> mapUpdateDtoCaptor;

    @Test
    void broadcast() {
        final var previousElement = mockDungeonGrass.get();
        previousElement.setCell(new DungeonCell(new DungeonCoord(0, 0), previousElement));

        final var walker = mockDungeonAutoWalker.get();
        walker.setPreviousCell(previousElement.getCell());
        walker.setCell(new DungeonCell(new DungeonCoord(1, 1)));

        new DungeonUpdater(this.messageTemplate, this.mapper).broadcast(walker);

        verify(this.messageTemplate)
                .convertAndSend(this.destinationCaptor.capture(), this.mapUpdateDtoCaptor.capture());

        assertThat(
                SIMPLE_BROKER_DESTINATION,
                equalTo(this.destinationCaptor.getValue()));
        assertThat(
                new MapUpdateDto(
                        new ElementDto(
                                walker.getId(),
                                walker.getAvatar(),
                                new CoordinateDto(
                                        walker.getCell().getCoord().x(),
                                        walker.getCell().getCoord().y()),
                                2),
                        new ElementDto(
                                previousElement.getId(),
                                previousElement.getAvatar(),
                                new CoordinateDto(
                                        previousElement.getCell().getCoord().x(),
                                        previousElement.getCell().getCoord().y()),
                                1)),
                equalTo(this.mapUpdateDtoCaptor.getValue()));
    }

}