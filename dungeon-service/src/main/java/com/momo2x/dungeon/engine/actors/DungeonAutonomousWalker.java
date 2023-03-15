package com.momo2x.dungeon.engine.actors;

import com.momo2x.dungeon.engine.movement.BounceStrategyType;
import com.momo2x.dungeon.engine.movement.DirectionType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DungeonAutonomousWalker extends DungeonWalker {

    @Getter
    private final BounceStrategyType bounceStrategy;

    @Getter
    private final int speed;

    public DungeonAutonomousWalker(
            final String id,
            final String avatar,
            final boolean blocker,
            final DirectionType direction,
            final int speed,
            final BounceStrategyType bounceStrategy) {
        super(id, avatar, blocker, direction);
        this.speed = speed;
        this.bounceStrategy = bounceStrategy;
    }

}