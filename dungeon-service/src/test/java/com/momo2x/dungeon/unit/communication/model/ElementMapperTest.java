package com.momo2x.dungeon.unit.communication.model;

import com.momo2x.dungeon.communication.model.CoordinateDto;
import com.momo2x.dungeon.communication.model.ElementDto;
import com.momo2x.dungeon.communication.model.ElementMapper;
import com.momo2x.dungeon.communication.model.ElementMapperImpl;
import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.movement.DirectionType;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class ElementMapperTest {

    private final ElementMapper mapper = new ElementMapperImpl();

    @Test
    void toDto() {
        final var walker = new DungeonWalker("id", "avatar", true, DirectionType.E);
        walker.setCell(new DungeonCell(new DungeonCoord(1, 1)));

        final var expected = new ElementDto("id", "avatar", new CoordinateDto(1, 1));

        MatcherAssert.assertThat(mapper.toDto(walker), Matchers.equalTo(expected));
    }

}