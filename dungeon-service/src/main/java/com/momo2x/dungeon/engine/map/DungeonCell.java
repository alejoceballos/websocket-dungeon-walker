package com.momo2x.dungeon.engine.map;

import com.momo2x.dungeon.engine.actors.DungeonElement;
import lombok.*;

import java.util.Objects;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class DungeonCell {

    @Getter
    private final DungeonCoord coord;

    @Getter
    @Setter
    private DungeonElement element;

    public boolean isBlocked() {
        return this.element != null && this.element.isBlocker();
    }

    @Override
    public String toString() {
        return "Cell " + (coord == null ? "(?,?)" : coord) + ": " + (element == null ? "empty" : element.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DungeonCell that = (DungeonCell) o;
        return coord.equals(that.coord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coord);
    }
}
