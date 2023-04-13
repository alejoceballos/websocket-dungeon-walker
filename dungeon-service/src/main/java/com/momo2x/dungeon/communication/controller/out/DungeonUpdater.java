package com.momo2x.dungeon.communication.controller.out;

import com.momo2x.dungeon.communication.model.ElementDto;
import com.momo2x.dungeon.communication.model.ElementMapper;
import com.momo2x.dungeon.communication.model.MapUpdateDto;
import com.momo2x.dungeon.engine.actors.DungeonWalker;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.momo2x.dungeon.config.WebSocketConstants.SIMPLE_BROKER_DESTINATION;

@Component
@RequiredArgsConstructor
public class DungeonUpdater {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final ElementMapper mapper;

    public void broadcast(final DungeonWalker walker) {
        final List<ElementDto> elements = new ArrayList<>();

        elements.add(mapper.toDto(walker));

        if (walker.isMoving()) {
            elements.add(mapper.toDto(walker.getPreviousCell().getTopElement()));
        }

        simpMessagingTemplate.convertAndSend(
                SIMPLE_BROKER_DESTINATION,
                new MapUpdateDto(elements.toArray(new ElementDto[0])));
    }
}

