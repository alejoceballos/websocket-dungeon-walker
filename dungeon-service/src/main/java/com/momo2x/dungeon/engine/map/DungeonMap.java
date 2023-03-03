package com.momo2x.dungeon.engine.map;

import com.momo2x.dungeon.config.DungeonProperties;
import com.momo2x.dungeon.engine.actors.DungeonWall;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Component
@RequiredArgsConstructor
public class DungeonMap {

    private final DungeonProperties properties;

    @Getter
    private Map<DungeonCoord, DungeonCell> map;

    @Getter
    private Set<DungeonCoord> walls;

    public int getWidth() {
        return this.properties.getWidth();
    }

    public int getHeight() {
        return this.properties.getHeight();
    }

    public DungeonCell getCellAt(final DungeonCoord coord) {
        return this.map.get(coord);
    }

    public boolean isLimit(final DungeonCoord coord) {
        return coord.x() == 0
                || coord.x() == this.getWidth() - 1
                || coord.y() == 0
                || coord.y() == this.getHeight() - 1;
    }

    @PostConstruct
    public void init() {
        this.map = new ConcurrentHashMap<>(this.getWidth() * this.getHeight());
        this.walls = new HashSet<>();

        final var wall = new DungeonWall("W", true);

        for (int x = 0; x < this.getWidth(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {
                final var coord = new DungeonCoord(x, y);
                final var cell = new DungeonCell(coord);

                if (isLimit(coord)) {
                    cell.setElement(wall);
                    this.walls.add(coord);
                }

                this.map.put(coord, cell);
            }
        }
    }

}
