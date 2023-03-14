package com.momo2x.dungeon.engine.map;

import com.momo2x.dungeon.engine.actors.DungeonElement;
import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.actors.DungeonWall;
import com.momo2x.dungeon.engine.actors.ElementException;
import com.momo2x.dungeon.engine.movement.DirectionType;
import com.momo2x.dungeon.engine.movement.MovementException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

@Slf4j
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
    private final Map<String, DungeonWalker> walkers;

    public DungeonCell getCellAt(final DungeonCoord coord) {
        return this.map.get(coord);
    }

    private static void validateWalkerDirection(DirectionType direction) throws MovementException {
        if (direction == null) {
            throw new MovementException("No direction given");
        }
    }

    private static void validateEnterMapCell(DungeonCoord coord, DungeonCell cell) throws CellException {
        validateMapCell(coord, cell);

        if (cell.getElement() != null && cell.getElement().isBlocker()) {
            throw new CellException("Cannot place element at %s. Already occupied".formatted(coord.toString()));
        }
    }

    private static void validateMapCell(DungeonCoord coord, DungeonCell cell) throws CellException {
        if (cell == null) {
            throw new CellException("No cell in coordinates %s".formatted(coord.toString()));
        }
    }

    private static void validateMapElement(DungeonElement element) throws ElementException {
        if (element == null) {
            throw new ElementException(
                    "It is not possible to place a null element",
                    new IllegalArgumentException("Element cannot be null"));
        }

        if (element.getId() == null) {
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

        cell.setElement(element);
        element.setCell(cell);

        if (element instanceof DungeonWalker walker && !this.walkers.containsValue(element)) {
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

        if (nextCell.getElement() == null || !nextCell.getElement().isBlocker()) {
            final var currCell = walker.getCell();

            nextCell.setElement(walker);
            currCell.setElement(null);

            walker.setCell(nextCell);
            walker.setPreviousCell(currCell);
        }

        walker.setDirection(direction);

        return walker;
    }

    private void validateWalkerId(String id) throws MovementException {
        if (id.isBlank()) {
            throw new MovementException("Can't move a walker without and ID");
        }

        if (!this.walkers.containsKey(id)) {
            throw new MovementException("Walker '%s' is not in the map".formatted(id));
        }
    }

    private void validateMapCoordinate(DungeonCoord coord) throws CoordinateException {
        if (coord == null) {
            throw new CoordinateException(
                    "It is not possible to have null coordinates in the map",
                    new IllegalArgumentException("Element cannot be null"));
        }

        if (coord.x() < 0 || coord.x() > this.width || coord.y() < 0 || coord.y() > this.height) {
            throw new CoordinateException("It is not possible to place an element outside of the map");
        }
    }

}
