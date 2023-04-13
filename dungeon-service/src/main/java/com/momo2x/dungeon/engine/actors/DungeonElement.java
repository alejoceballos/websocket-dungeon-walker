package com.momo2x.dungeon.engine.actors;

import com.momo2x.dungeon.engine.map.DungeonCell;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@RequiredArgsConstructor
public class DungeonElement {

    @Getter
    private final String id;

    @Getter
    private final String avatar;

    @Getter
    private final boolean blocker;

    @Getter
    @Setter
    protected DungeonCell cell;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DungeonElement that = (DungeonElement) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
