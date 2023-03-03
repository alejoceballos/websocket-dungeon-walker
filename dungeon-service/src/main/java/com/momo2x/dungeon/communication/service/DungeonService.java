package com.momo2x.dungeon.communication.service;

import com.momo2x.dungeon.engine.map.DungeonMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DungeonService {

    private final DungeonMap map;

    public DungeonMap getMap() {
        return this.map;
    }

}
