package com.momo2x.dungeon.engine.map;

import com.momo2x.dungeon.engine.actors.DungeonElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
        return "Cell " +
                (this.coord == null ? "(?,?)" : this.coord) +
                ": " +
                (this.element == null ? "empty" : this.element.getId());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DungeonCell that = (DungeonCell) o;
        return this.coord.equals(that.coord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.coord);
    }
}
