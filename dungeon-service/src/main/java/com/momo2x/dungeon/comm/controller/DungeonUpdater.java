package com.momo2x.dungeon.comm.controller;

import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.comm.model.WalkerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DungeonUpdater {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final WalkerMapper mapper;

    public void update(final DungeonWalker walker) {
        simpMessagingTemplate.convertAndSendToUser("test", "/dungeon", mapper.toDto(walker));
    }
}
