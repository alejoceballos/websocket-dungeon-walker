package com.momo2x.dungeon.config.map;

import com.momo2x.dungeon.engine.actors.DungeonAutonomousWalker;
import com.momo2x.dungeon.engine.actors.DungeonElement;
import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.actors.ElementType;
import com.momo2x.dungeon.engine.interaction.bounce.BounceStrategyType;
import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.map.DungeonMap;
import com.momo2x.dungeon.engine.movement.DirectionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.momo2x.dungeon.engine.actors.ElementType.WALKER;
import static java.lang.Math.max;
import static java.util.Objects.isNull;

@Slf4j
@RequiredArgsConstructor
public class MapCreator {
    private final MapMetadata mapMetadata;

    private final Map<String, CatalogueItem> catalogueMap;

    public DungeonMap create() throws MalformedMapException, MalformedCatalogueException {
        log.info("Creating the dungeon map");

        final var map = new ConcurrentHashMap<DungeonCoord, DungeonCell>();
        final var walkers = new HashMap<String, DungeonWalker>();

        var maxX = 0;
        var maxY = 0;
        var maxLayerIndex = 0;

        for (final var elementCoords : this.mapMetadata.elementsCoords().entrySet()) {
            final var elementId = elementCoords.getKey();

            if (isNull(elementId)) {
                continue;
            }

            final var catalogueItem = this.catalogueMap.get(elementId);

            if (isNull(catalogueItem)) {
                throw new MalformedCatalogueException(
                        "Element [%s] in map has no correspondent in object's catalogue".formatted(elementId));
            }

            for (final var coord : elementCoords.getValue()) {
                final var layerIndex = this.mapMetadata.elementsLayers().get(elementId);
                final var element = this.createElement(elementId, catalogueItem, layerIndex);
                final var cell = this.getOrCreateCellAndAddElement(map, coord, element);

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

                maxX = max(coord.x(), maxX);
                maxY = max(coord.y(), maxY);
                maxLayerIndex = max(layerIndex, maxLayerIndex);
            }
        }

        return new DungeonMap(maxX + 1, maxY + 1, maxLayerIndex + 2, map, walkers);
    }

    private DungeonElement createElement(
            final String id,
            final CatalogueItem item,
            final int mapLayer) throws MalformedElementException {
        if (isNull(id)) {
            throw new MalformedElementException("All elements must have an ID");
        }

        final var type = ElementType.valueOf(item.type().toUpperCase());

        if (type == WALKER) {
            return new DungeonAutonomousWalker(
                    id,
                    item.avatar(),
                    item.blocker(),
                    mapLayer,
                    DirectionType.valueOf(item.direction()),
                    item.speed(),
                    BounceStrategyType.valueOf(item.bounce().toUpperCase()));
        }

        return new DungeonElement(id, item.avatar(), item.blocker(), mapLayer);
    }

    private DungeonCell getOrCreateCellAndAddElement(ConcurrentHashMap<DungeonCoord, DungeonCell> map, DungeonCoord coord, DungeonElement element) throws MalformedCellException, MalformedElementException {
        var cell = map.get(coord);

        if (Objects.isNull(cell)) {
            cell = new DungeonCell(coord, element);
            element.setCell(cell);

            return map.put(coord, cell);
        }

        final var elementInCell = cell.removeTopElement();

        if (Objects.isNull(elementInCell)) {
            throw new MalformedCellException(
                    "Cell %s was created with no element in it. There can be no empty cell in the map");
        }

        if (element.getLayerIndex() == elementInCell.getLayerIndex()) {
            throw new MalformedElementException(
                    "Two elements cannot be on the same layer in the same cell. " +
                            "Elements %s and %s in cel %s have the layer index %d".formatted(
                                    elementInCell.getId(),
                                    element.getId(),
                                    cell,
                                    element.getLayerIndex()));
        }

        if (element.getLayerIndex() < elementInCell.getLayerIndex()) {
            cell.addElementToTop(element);
            cell.addElementToTop(elementInCell);

        } else {
            cell.addElementToTop(elementInCell);
            cell.addElementToTop(element);
        }

        element.setCell(cell);

        return cell;
    }

}
