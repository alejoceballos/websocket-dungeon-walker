package com.momo2x.dungeon.engine.actors;

import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public abstract class DungeonElement {

    @Getter
    private final String id;

    @Getter
    private final boolean blocker;

    @Getter
    @Setter
    protected DungeonCell cell;

}
