package com.momo2x.dungeon.engine.movement;

import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.map.DungeonMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class MovementManager {

    private final DungeonMap map;

    @Getter
    private final DungeonWalker walker;

    private final BounceStrategy bounce;

    public void move() {
        final var nextCoord = this.walker.getCell().getCoord().getCoordAt(this.walker.getDirection());

        final var currCell = this.walker.getCell();
        final var nextCell = this.map.getCellAt(nextCoord);

        if (Objects.isNull(nextCell)) {
            throw new RuntimeException("Unmapped cell");

        } else if (nextCell.getElement() != null && nextCell.getElement().isBlocker()) {
            this.walker.setDirection(bounce.bounceDirection());

        } else {
            nextCell.setElement(this.walker);
            currCell.setElement(null);

            walker.setCell(nextCell);
            walker.setPreviousCell(currCell);
        }
    }

    public void enterMap(final DungeonCoord coord) {
        final var cell = this.map.getCellAt(coord);

        if (Objects.isNull(cell)) {
            throw new RuntimeException("Unmapped cell");

        } else if (cell.getElement() != null && cell.getElement().isBlocker()) {
            throw new RuntimeException("Cannot enter the map on an occupied cell");

        } else {
            cell.setElement(this.walker);
            walker.setCell(cell);
            walker.setPreviousCell(cell);
        }
    }
}
