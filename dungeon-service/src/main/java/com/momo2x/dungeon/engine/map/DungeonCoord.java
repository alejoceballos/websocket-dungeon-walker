package com.momo2x.dungeon.engine.map;

import com.momo2x.dungeon.engine.movement.DirectionType;

import static com.momo2x.dungeon.engine.movement.DirectionType.EASTERN;
import static com.momo2x.dungeon.engine.movement.DirectionType.NORTHERN;
import static com.momo2x.dungeon.engine.movement.DirectionType.SOUTHERN;
import static com.momo2x.dungeon.engine.movement.DirectionType.WESTERN;

public record DungeonCoord(int x, int y) {

    public DungeonCoord getCoordAt(final DirectionType direction) {
        assert direction != null : "Direction cannot be null";

        int x = this.x;
        int y = this.y;

        if (NORTHERN.contains(direction)) {
            y--;
        }

        if (SOUTHERN.contains(direction)) {
            y++;
        }

        if (EASTERN.contains(direction)) {
            x++;
        }

        if (WESTERN.contains(direction)) {
            x--;
        }

        return new DungeonCoord(x, y);
    }

    public DirectionType calculateDirection(final DungeonCoord coord) {
        final var coordDirection = new StringBuilder()
                .append(coord.y == this.y ? "" : coord.y < this.y ? "N" : "S")
                .append(coord.x == this.x ? "" : coord.x < this.x ? "W" : "E");

        return coordDirection.isEmpty() ? null : DirectionType.valueOf(coordDirection.toString());
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
