package com.momo2x.dungeon.engine.movement;

import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.map.DungeonMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class MovementManager {

    private final DungeonMap map;

    @Getter
    private final DungeonWalker walker;

    @Getter
    private final BounceStrategy bounce;

    public void move() throws MovementException {
        final var nextCoord = this.walker.getCoord().getCoordAt(this.walker.getDirection());

        final var currCell = this.walker.getCell();
        final var nextCell = this.map.getCellAt(nextCoord);

        if (Objects.isNull(nextCell)) {
            throw new MovementException(
                    "Trying to move to an unmapped cell. Some uncaught error happened while loading the map");

        } else if (nextCell.getElement() != null && nextCell.getElement().isBlocker()) {
            this.walker.setDirection(this.bounce.bounceDirection());

        } else {
            nextCell.setElement(this.walker);
            currCell.setElement(null);

            this.walker.setCell(nextCell);
            this.walker.setPreviousCell(currCell);
        }
    }

    public long calculateSleepTme() {
        final var speed = walker.getSpeed() < 1 ? 1 : walker.getSpeed() > 10 ? 10 : walker.getSpeed();
        return 10 * (100 - ((speed - 1) * 10L));
    }

}
