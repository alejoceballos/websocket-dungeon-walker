package com.momo2x.dungeon.engine.actors;

import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.movement.DirectionType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DungeonWalker extends DungeonElement {

    @Getter
    @Setter
    private DirectionType direction;

    @Getter
    @Setter
    private DungeonCell previousCell;

    public DungeonWalker(
            final String id,
            final String avatar,
            final boolean blocker,
            final DirectionType direction) {
        super(id, avatar, blocker);
        this.direction = direction;
    }

    public DungeonCoord getCoord() {
        return this.getCell().getCoord();
    }

    public boolean isMoving() {
        return !this.getCell().equals(this.previousCell);
    }

}
