package com.momo2x.dungeon.config.map;

import com.momo2x.dungeon.engine.actors.DungeonAutonomousWalker;
import com.momo2x.dungeon.engine.actors.DungeonElement;
import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.actors.DungeonWall;
import com.momo2x.dungeon.engine.actors.ElementType;
import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.map.DungeonMap;
import com.momo2x.dungeon.engine.movement.BounceStrategyType;
import com.momo2x.dungeon.engine.movement.DirectionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
public class MapCreator {
    private final Map<String, List<DungeonCoord>> elementsCoordsMap;

    private final Map<String, CatalogueItem> catalogueMap;

    public DungeonMap create() throws MalformedMapException, MalformedCatalogueException {
        log.info("Creating the dungeon map");

        final var map = new ConcurrentHashMap<DungeonCoord, DungeonCell>();
        final var walls = new HashSet<DungeonWall>();
        final var walkers = new HashMap<String, DungeonWalker>();

        var maxX = 0;
        var maxY = 0;

        for (var elementCoords : this.elementsCoordsMap.entrySet()) {
            final var elementId = elementCoords.getKey();

            DungeonElement element = null;

            if (elementId != null) {
                final var catalogueItem = this.catalogueMap.get(elementId);

                if (catalogueItem == null) {
                    throw new MalformedCatalogueException(
                            "Element [%s] in map has no correspondent in object's catalogue".formatted(elementId));
                }

                element = createElement(elementId, catalogueItem);

                if (element instanceof DungeonWall wall) {
                    walls.add(wall);

                } else if (element instanceof DungeonWalker walker) {
                    if (walkers.containsKey(elementId)) {
                        throw new MalformedMapException(
                                ("Element [%s] has been already processed. " +
                                        "A '%s' cannot be in two coordinates at the same time")
                                        .formatted(walker.getId(), walker.getClass().getSimpleName()));
                    }

                    walkers.put(elementId, walker);
                }
            }

            for (var coord : elementCoords.getValue()) {
                final var cell = new DungeonCell(coord, element);

                if (element instanceof DungeonWalker walker) {
                    walker.setCell(cell);
                    walker.setPreviousCell(cell);
                }

                map.put(coord, cell);

                maxX = coord.x() > maxX ? coord.x() : maxX;
                maxY = coord.y() > maxY ? coord.y() : maxY;
            }
        }

        return new DungeonMap(maxX + 1, maxY + 1, map, walls, walkers);
    }

    private DungeonElement createElement(final String id, final CatalogueItem item) throws MalformedElementException {
        if (id == null) {
            throw new MalformedElementException("All elements must have an ID");
        }

        final var type = ElementType.valueOf(item.type().toUpperCase());

        return switch (type) {
            case EMPTY -> null;
            case WALL -> new DungeonWall(id, item.avatar(), item.blocker());
            case WALKER -> new DungeonAutonomousWalker(
                    id,
                    item.avatar(),
                    item.blocker(),
                    DirectionType.valueOf(item.direction()),
                    item.speed(),
                    BounceStrategyType.valueOf(item.bounce().toUpperCase()));
        };
    }

}
