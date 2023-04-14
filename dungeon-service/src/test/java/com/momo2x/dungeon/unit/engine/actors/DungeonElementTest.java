package com.momo2x.dungeon.unit.engine.actors;

import com.momo2x.dungeon.engine.actors.DungeonAutonomousWalker;
import com.momo2x.dungeon.engine.actors.DungeonWalker;
import org.junit.jupiter.api.Test;

import static com.momo2x.dungeon.engine.movement.DirectionType.E;
import static com.momo2x.dungeon.engine.movement.DirectionType.W;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

class DungeonElementTest {

    private final DungeonWalker walker = new DungeonWalker("id", "avatar", true, 0, E);
    private final DungeonAutonomousWalker autoWalker = new DungeonAutonomousWalker("id", "avatar", true, 1, E, 10, null);

    @Test
    void testEquals() {
        final var walkerEqual = new DungeonWalker("id", "diff avatar", false, 99, W);
        assertThat(walkerEqual, equalTo(this.walker));

        final var autoWalkerEqual = new DungeonAutonomousWalker("id", "diff avatar", false, 98, W, 1, null);
        assertThat(this.autoWalker, equalTo(autoWalkerEqual));
    }

    @Test
    void testNotEquals() {
        assertThat(this.walker, not(equalTo(this.autoWalker)));

        final var walkerNotEqual = new DungeonWalker("diff id", "avatar", true, 0, E);
        assertThat(walkerNotEqual, not(equalTo(this.walker)));

        final var autoWalkerNotEqual = new DungeonAutonomousWalker("diff id", "avatar", true, 1, E, 10, null);
        assertThat(this.autoWalker, not(equalTo(autoWalkerNotEqual)));
    }

}