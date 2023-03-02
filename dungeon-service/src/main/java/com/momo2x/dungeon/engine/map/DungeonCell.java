package com.momo2x.dungeon.engine.map;

import com.momo2x.dungeon.engine.actors.DungeonElement;
import lombok.*;

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
}
