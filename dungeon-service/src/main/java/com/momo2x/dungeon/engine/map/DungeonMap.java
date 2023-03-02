package com.momo2x.dungeon.engine.map;

import com.momo2x.dungeon.engine.actors.DungeonWall;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class DungeonMap {

    private final int width;

    private final int height;

    private Map<DungeonCoord, DungeonCell> map;

    public void init() {
        this.map = new ConcurrentHashMap<>(width * height);

        final var wall = new DungeonWall("W", true);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final var coord = new DungeonCoord(x, y);
                final var cell = new DungeonCell(coord);

                if (isLimit(coord)) {
                    cell.setElement(wall);
                }

                this.map.put(coord, cell);
            }
        }
    }

    public DungeonCell getCell(final DungeonCoord coord) {
        return this.map.get(coord);
    }

    public boolean isLimit(final DungeonCoord coord) {
        return coord.x() == 0
                || coord.x() == width - 1
                || coord.y() == 0
                || coord.y() == height - 1;
    }

}
