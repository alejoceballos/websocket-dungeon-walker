package com.momo2x.dungeon.engine.movement;

import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonMap;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Random;

import static com.momo2x.dungeon.engine.movement.DungeonDirectionType.*;

@RequiredArgsConstructor
public class SimpleBounceStrategy implements BounceStrategy {

    private final DungeonMap map;

    private final DungeonWalker walker;

    private final Random randomizer = new Random();

    @Override
    public DungeonDirectionType bounceDirection() {
        return switch (this.walker.getDirection()) {
            case SE -> calculateFullBounce(NE, SW, S, E);
            case NW -> calculateFullBounce(NE, SW, N, W);
            case NE -> calculateFullBounce(NW, SE, N, E);
            case SW -> calculateFullBounce(NW, SE, S, S);
            default -> this.walker.getDirection().getOpposite();
        };
    }

    private DungeonDirectionType calculateFullBounce(
            DungeonDirectionType dir01,
            DungeonDirectionType dir02,
            DungeonDirectionType dir03,
            DungeonDirectionType dir04) {

        return Optional
                .ofNullable(calculateHalfBounceFromDirection(dir01, dir02))
                .orElseGet(() -> Optional
                        .ofNullable(calculateHalfBounceFromDirection(dir03, dir04))
                        .orElseGet(() -> this.walker.getDirection().getOpposite()));
    }

    private DungeonDirectionType calculateHalfBounceFromDirection(
            DungeonDirectionType dir01,
            DungeonDirectionType dir02) {
        final var cell01 = getCellByDirection(dir01);
        final var cell02 = getCellByDirection(dir02);

        return calculateHalfBounceFromCell(cell01, cell02);

    }

    private DungeonCell getCellByDirection(DungeonDirectionType direction) {
        final var coord = this.walker.getCoord().getCoordAt(direction);
        return Optional.ofNullable(this.map.getCell(coord)).orElseGet(() -> new DungeonCell(coord));
    }

    private DungeonDirectionType calculateHalfBounceFromCell(DungeonCell cell01, DungeonCell cell02) {
        final var cells = randomizeCells(cell01, cell02);

        if (!cells[0].isBlocked()) {
            return walker.getCoord().calculateDirection(cells[0].getCoord());

        } else if (!cells[1].isBlocked()) {
            return walker.getCoord().calculateDirection(cells[1].getCoord());
        }

        return null;
    }

    private DungeonCell[] randomizeCells(DungeonCell cell01, DungeonCell cell02) {
        final var cells = new DungeonCell[2];
        final var idx = randomizer.nextInt(2);

        cells[idx] = cell01;
        cells[1 - idx] = cell02;

        return cells;
    }
}
