package com.momo2x.dungeon.unit.communication.model;

import com.momo2x.dungeon.communication.model.CoordinateDto;
import com.momo2x.dungeon.communication.model.ElementDto;
import com.momo2x.dungeon.communication.model.ElementMapper;
import com.momo2x.dungeon.communication.model.ElementMapperImpl;
import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import org.junit.jupiter.api.Test;

import static com.momo2x.dungeon.engine.movement.DirectionType.E;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ElementMapperTest {

    private final ElementMapper mapper = new ElementMapperImpl();

    @Test
    void toDto() {
        final var walker = new DungeonWalker("id", "avatar", true, 3, E);
        walker.setCell(new DungeonCell(new DungeonCoord(1, 1)));

        final var expected = new ElementDto("id", "avatar", new CoordinateDto(1, 1), 3);

        assertThat(this.mapper.toDto(walker), equalTo(expected));
    }

}