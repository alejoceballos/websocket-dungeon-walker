package com.momo2x.dungeon.config.map;

import com.momo2x.dungeon.engine.actors.DungeonAutonomousWalker;
import com.momo2x.dungeon.engine.actors.DungeonElement;
import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.actors.ElementType;
import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.map.DungeonMap;
import com.momo2x.dungeon.engine.movement.BounceStrategyType;
import com.momo2x.dungeon.engine.movement.DirectionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
public class MapCreator {
    private final Map<String, List<DungeonCoord>> elementsCoordsMap;

    private final Map<String, CatalogueItem> catalogueMap;

    public DungeonMap create() throws MalformedMapException, MalformedCatalogueException {
        log.info("Creating the dungeon map");

        final var map = new ConcurrentHashMap<DungeonCoord, DungeonCell>();
        final var walkers = new HashMap<String, DungeonWalker>();

        var maxX = 0;
        var maxY = 0;

        for (final var elementCoords : this.elementsCoordsMap.entrySet()) {
            final var elementId = elementCoords.getKey();

            if (Objects.nonNull(elementId)) {
                final var catalogueItem = this.catalogueMap.get(elementId);

                if (Objects.isNull(catalogueItem)) {
                    throw new MalformedCatalogueException(
                            "Element [%s] in map has no correspondent in object's catalogue".formatted(elementId));
                }

                for (final var coord : elementCoords.getValue()) {
                    DungeonElement element = createElement(elementId, catalogueItem);
                    final var cell = new DungeonCell(coord, element);

                    element.setCell(cell);
                    map.put(coord, cell);

                    maxX = Math.max(coord.x(), maxX);
                    maxY = Math.max(coord.y(), maxY);

                    if (element instanceof final DungeonWalker walker) {
                        if (walkers.containsKey(elementId)) {
                            throw new MalformedMapException(
                                    ("Element [%s] has been already processed. " +
                                            "A '%s' cannot be in two coordinates at the same time")
                                            .formatted(walker.getId(), walker.getClass().getSimpleName()));
                        }

                        walkers.put(elementId, walker);
                        walker.setPreviousCell(cell);
                    }
                }
            }
        }

        return new DungeonMap(maxX + 1, maxY + 1, map, walkers);
    }

    private DungeonElement createElement(final String id, final CatalogueItem item) throws MalformedElementException {
        if (id == null) {
            throw new MalformedElementException("All elements must have an ID");
        }

        final var type = ElementType.valueOf(item.type().toUpperCase());

        if (type == ElementType.WALKER) {
            return new DungeonAutonomousWalker(
                    id,
                    item.avatar(),
                    item.blocker(),
                    DirectionType.valueOf(item.direction()),
                    item.speed(),
                    BounceStrategyType.valueOf(item.bounce().toUpperCase()));
        }

        return new DungeonElement(id, item.avatar(), item.blocker());
    }

}
