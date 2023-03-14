package com.momo2x.dungeon.communication.controller;

import com.momo2x.dungeon.communication.model.WalkerMapper;
import com.momo2x.dungeon.engine.actors.DungeonWalker;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import static com.momo2x.dungeon.config.WebSocketConstants.SIMPLE_BROKER_DESTINATION;

@Component
@RequiredArgsConstructor
public class DungeonUpdater {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final WalkerMapper mapper;

    public void broadcast(final DungeonWalker walker) {
        simpMessagingTemplate.convertAndSend(SIMPLE_BROKER_DESTINATION, mapper.toDto(walker));
    }
}

