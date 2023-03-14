package com.momo2x.dungeon.engine.movement;

import com.momo2x.dungeon.engine.actors.DungeonAutonomousWalker;
import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.map.CellException;
import com.momo2x.dungeon.engine.map.DungeonMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MovementManager {

    private final DungeonMap map;

    @Getter
    private final DungeonAutonomousWalker walker;

    @Getter
    private final BounceStrategy bounce;

    public void move() throws MovementException, CellException {
        final var currCell = this.walker.getCell();
        final DungeonWalker walker = this.map.move(this.walker);

        if (currCell != walker.getCell()) {
            return;
        }

        final var nextCoord = walker.getCoord().getCoordAt(walker.getDirection());
        final var nextCell = this.map.getCellAt(nextCoord);

        if (nextCell.getElement() != null && nextCell.getElement().isBlocker()) {
            this.walker.setDirection(this.bounce.bounceDirection());
        }

    }

    public long calculateSleepTme() {
        final var speed = walker.getSpeed() < 1 ? 1 : walker.getSpeed() > 10 ? 10 : walker.getSpeed();
        return 10 * (100 - ((speed - 1) * 10L));
    }

}
