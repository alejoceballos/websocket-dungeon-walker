package com.momo2x.dungeon.communication.service;

import com.momo2x.dungeon.communication.controller.DungeonUpdater;
import com.momo2x.dungeon.engine.map.CellException;
import com.momo2x.dungeon.engine.map.DungeonMap;
import com.momo2x.dungeon.engine.movement.DirectionType;
import com.momo2x.dungeon.engine.movement.MovementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final DungeonMap map;

    private final DungeonUpdater updater;

    public void move(final String id, final DirectionType direction) throws CellException, MovementException {
        final var walker = this.map.move(id, direction);
        this.updater.broadcast(walker);
    }

}
