package com.momo2x.dungeon.engine.actors;

import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.movement.BounceStrategyType;
import com.momo2x.dungeon.engine.movement.DirectionType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DungeonWalker extends DungeonElement {

    @Getter
    private final BounceStrategyType bounceStrategy;
    @Getter
    @Setter
    private DirectionType direction;

    @Getter
    private final int speed;

    @Getter
    @Setter
    private DungeonCell previousCell;

    public DungeonWalker(
            final String id,
            final String avatar,
            final boolean blocker,
            final DirectionType direction,
            final int speed,
            final BounceStrategyType bounceStrategy) {
        super(id, avatar, blocker);
        this.direction = direction;
        this.speed = speed;
        this.bounceStrategy = bounceStrategy;
    }

    public DungeonCoord getCoord() {
        return this.getCell().getCoord();
    }

}
