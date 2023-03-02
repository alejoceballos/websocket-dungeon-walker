package com.momo2x.dungeon.engine.actors;

import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.map.DungeonMap;
import com.momo2x.dungeon.engine.movement.DungeonDirectionType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

@Slf4j
public class DungeonWalker extends DungeonElement {

    @Getter
    @Setter
    private DungeonDirectionType direction;

    @Getter
    @Setter
    private DungeonCell previousCell;

    public DungeonWalker(
            final String id,
            final boolean blocker,
            final DungeonDirectionType direction) {
        super(id, true);
        this.direction = direction;
    }

    public DungeonCoord getCoord() {
        return this.getCell().getCoord();
    }

}
