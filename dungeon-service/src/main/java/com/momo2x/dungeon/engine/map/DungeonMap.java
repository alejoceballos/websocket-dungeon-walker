package com.momo2x.dungeon.engine.map;

import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.actors.DungeonWall;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class DungeonMap {

    @Getter
    private final int width;

    @Getter
    private final int height;

    @Getter
    private final Map<DungeonCoord, DungeonCell> map;

    @Getter
    private final Set<DungeonWall> walls;

    @Getter
    private final Set<DungeonWalker> walkers;

    public DungeonCell getCellAt(final DungeonCoord coord) {
        return this.map.get(coord);
    }

}
