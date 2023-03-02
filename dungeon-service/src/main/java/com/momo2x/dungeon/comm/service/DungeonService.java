package com.momo2x.dungeon.comm.service;

import com.momo2x.dungeon.config.DungeonProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DungeonService {

    private final DungeonProperties dungeon;

    public DungeonProperties getMap() {
        return dungeon;
    }

}
