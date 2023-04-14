package com.momo2x.dungeon.engine.map;

import com.momo2x.dungeon.engine.actors.DungeonElement;
import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.actors.ElementException;
import com.momo2x.dungeon.engine.movement.DirectionType;
import com.momo2x.dungeon.engine.movement.MovementException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static java.util.Objects.isNull;

@Slf4j
@RequiredArgsConstructor
public class DungeonMap {

    @Getter
    private final int width;

    @Getter
    private final int height;

    @Getter
    private final int numOfLayers;

    @Getter
    private final Map<DungeonCoord, DungeonCell> map;

    @Getter
    private final Map<String, DungeonWalker> walkers;

    public DungeonCell getCellAt(final DungeonCoord coord) {
        return this.map.get(coord);
    }

    private static void validateWalkerDirection(final DirectionType direction) throws MovementException {
        if (isNull(direction)) {
            throw new MovementException("No direction given");
        }
    }

    private static void validateEnterMapCell(final DungeonCoord coord, final DungeonCell cell) throws CellException {
        validateMapCell(coord, cell);

        if (cell.isBlocked()) {
            throw new CellException("Cannot place element at %s. Already occupied".formatted(coord.toString()));
        }
    }

    private static void validateMapCell(final DungeonCoord coord, final DungeonCell cell) throws CellException {
        if (isNull(cell)) {
            throw new CellException("No cell in coordinates %s".formatted(coord.toString()));
        }
    }

    private static void validateMapElement(final DungeonElement element) throws ElementException {
        if (isNull(element)) {
            throw new ElementException(
                    "It is not possible to place a null element",
                    new IllegalArgumentException("Element cannot be null"));
        }

        if (isNull(element.getId())) {
            throw new ElementException("Elements must have an ID. For walkers, this ID must be unique");
        }
    }

    public void placeElement(
            final DungeonElement element,
            final DungeonCoord coord) throws ElementException, CoordinateException, CellException {
        validateMapElement(element);
        validateMapCoordinate(coord);

        final var cell = this.map.get(coord);

        validateEnterMapCell(coord, cell);

        log.info("Placing element '{}' at {}", element.getId(), cell.getCoord().toString());

        cell.addElementToTop(element);
        element.setCell(cell);

        if (element instanceof final DungeonWalker walker && !this.walkers.containsValue(element)) {
            this.walkers.put(walker.getId(), walker);
            walker.setCell(cell);
            walker.setPreviousCell(cell);
        }
    }

    public DungeonWalker move(final DungeonWalker walker) throws MovementException, CellException {
        return this.move(walker.getId(), walker.getDirection());
    }

    public DungeonWalker move(final String id, final DirectionType direction) throws MovementException, CellException {
        validateWalkerId(id);
        validateWalkerDirection(direction);

        final var walker = this.walkers.get(id);
        final var nextCoord = walker.getCoord().getCoordAt(direction);
        final var nextCell = this.map.get(nextCoord);

        validateMapCell(nextCoord, nextCell);

        if (!nextCell.isBlocked()) {
            final var currCell = walker.getCell();

            nextCell.addElementToTop(walker);
            currCell.removeTopElement();

            walker.setCell(nextCell);
            walker.setPreviousCell(currCell);
        }

        walker.setDirection(direction);

        return walker;
    }

    private void validateWalkerId(final String id) throws MovementException {
        if (isNull(id)) {
            throw new MovementException("ID is null!!! Can't move a walker with no ID");
        }

        if (id.isBlank()) {
            throw new MovementException("Can't move a walker without and ID");
        }

        if (!this.walkers.containsKey(id)) {
            throw new MovementException("Walker '%s' is not in the map".formatted(id));
        }
    }

    private void validateMapCoordinate(final DungeonCoord coord) throws CoordinateException {
        if (isNull(coord)) {
            throw new CoordinateException(
                    "It is not possible to have null coordinates in the map",
                    new IllegalArgumentException("Element cannot be null"));
        }

        if (coord.x() < 0 || coord.x() > this.width || coord.y() < 0 || coord.y() > this.height) {
            throw new CoordinateException("It is not possible to place an element outside of the map");
        }
    }

}
