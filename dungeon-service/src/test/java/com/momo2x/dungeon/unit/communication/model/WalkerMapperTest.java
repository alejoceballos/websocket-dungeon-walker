package com.momo2x.dungeon.unit.communication.model;

import com.momo2x.dungeon.communication.model.CoordinateDto;
import com.momo2x.dungeon.communication.model.WalkerDto;
import com.momo2x.dungeon.communication.model.WalkerMapper;
import com.momo2x.dungeon.communication.model.WalkerMapperImpl;
import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.movement.DirectionType;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class WalkerMapperTest {

    private final WalkerMapper mapper = new WalkerMapperImpl();

    @Test
    void toDto() {
        final var walker = new DungeonWalker("id", "avatar", true, DirectionType.E);
        walker.setPreviousCell(new DungeonCell(new DungeonCoord(0, 0)));
        walker.setCell(new DungeonCell(new DungeonCoord(1, 1)));

        final var expected = new WalkerDto("id", "avatar", new CoordinateDto(0, 0), new CoordinateDto(1, 1));

        MatcherAssert.assertThat(mapper.toDto(walker), Matchers.equalTo(expected));
    }

}