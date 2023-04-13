package com.momo2x.dungeon.engine.movement;

import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.map.DungeonMap;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;

import static com.momo2x.dungeon.engine.movement.DirectionType.E;
import static com.momo2x.dungeon.engine.movement.DirectionType.EASTERN;
import static com.momo2x.dungeon.engine.movement.DirectionType.N;
import static com.momo2x.dungeon.engine.movement.DirectionType.NE;
import static com.momo2x.dungeon.engine.movement.DirectionType.NW;
import static com.momo2x.dungeon.engine.movement.DirectionType.S;
import static com.momo2x.dungeon.engine.movement.DirectionType.SE;
import static com.momo2x.dungeon.engine.movement.DirectionType.SW;
import static com.momo2x.dungeon.engine.movement.DirectionType.W;
import static com.momo2x.dungeon.engine.movement.DirectionType.WESTERN;

@RequiredArgsConstructor
public class SimpleBounceStrategy implements BounceStrategy {

    private final DungeonMap map;

    private final DungeonWalker walker;

    private final Random randomizer = new Random();

    @Override
    public DirectionType bounceDirection() {
        final var currDirection = this.walker.getDirection();

        DirectionType nextDirection = null;

        if (currDirection == NE) {
            nextDirection = getNextDirectionOptions(SE, NW, NE.getOpposite());

        } else if (currDirection == SE) {
            nextDirection = getNextDirectionOptions(NE, SW, SE.getOpposite());

        } else if (currDirection == NW) {
            nextDirection = getNextDirectionOptions(SW, NE, NW.getOpposite());

        } else if (currDirection == SW) {
            nextDirection = getNextDirectionOptions(SE, NW, SW.getOpposite());

        } else if (currDirection == S) {
            nextDirection = getNextDirectionOptions(S.getOpposite());

        } else if (currDirection == N) {
            nextDirection = getNextDirectionOptions(N.getOpposite());

        } else if (currDirection == E) {
            nextDirection = getNextDirectionOptions(E.getOpposite());

        } else if (currDirection == W) {
            nextDirection = getNextDirectionOptions(W.getOpposite());
        }

        return nextDirection == null ? tryEachCoordinateOrGiveUp() : nextDirection;
    }

    private DirectionType getNextDirectionOptions(final DirectionType... directionOptions) {
        for (final var direction : directionOptions) {
            if (this.isNextDirectionValid(direction)) {
                return direction;
            }
        }

        return null;
    }

    private boolean isNextDirectionValid(final DirectionType nextDirection) {
        final var currCoord = this.walker.getCoord();
        final var nextCoord = currCoord.getCoordAt(nextDirection);

        return !this.map.getCellAt(nextCoord).isBlocked();
    }

    private DirectionType tryEachCoordinateOrGiveUp() {
        final var direction = this.walker.getDirection();

        final var clockwiseCoordinates = List.of(N, NE, E, SE, S, SW, W, NW);
        final var antiClockwiseCoordinates = List.of(N, NW, W, SW, S, SE, E, NE);
        List<DirectionType> orderedDirections;

        if (EASTERN.contains(direction)) {
            orderedDirections = clockwiseCoordinates;
        } else if (WESTERN.contains(direction)) {
            orderedDirections = antiClockwiseCoordinates;
        } else if (randomizer.nextBoolean()) {
            orderedDirections = clockwiseCoordinates;
        } else {
            orderedDirections = antiClockwiseCoordinates;
        }

        final var currCoord = this.walker.getCoord();

        var directionsCurrentIdx = orderedDirections.indexOf(direction) + 1;

        for (var i = 0; i < orderedDirections.size() - 1; i++) {
            final var currDirection = orderedDirections.get(directionsCurrentIdx);
            final var coordAtDirection = currCoord.getCoordAt(currDirection);
            final var cellAtDirection = this.map.getCellAt(coordAtDirection);

            if (!cellAtDirection.isBlocked()) {
                return currDirection;
            }

            if (directionsCurrentIdx == orderedDirections.size() - 1) {
                directionsCurrentIdx = 0;
            } else {
                directionsCurrentIdx++;
            }
        }

        return direction;
    }
}
