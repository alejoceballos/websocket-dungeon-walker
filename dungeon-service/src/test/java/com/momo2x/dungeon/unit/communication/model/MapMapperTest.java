package com.momo2x.dungeon.unit.communication.model;

import com.momo2x.dungeon.communication.model.CoordinateDto;
import com.momo2x.dungeon.communication.model.ElementDto;
import com.momo2x.dungeon.communication.model.MapMapper;
import com.momo2x.dungeon.communication.model.MapMapperImpl;
import com.momo2x.dungeon.engine.actors.DungeonElement;
import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.map.DungeonMap;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

public class MapMapperTest {

    private final MapMapper mapper = new MapMapperImpl();

    @Test
    void toDto() {
        final var source = new HashMap<DungeonCoord, DungeonCell>() {{
            put(
                    new DungeonCoord(0, 0),
                    new DungeonCell(
                            new DungeonCoord(0, 0),
                            new DungeonElement("AAA", "A", true)));
            put(
                    new DungeonCoord(1, 1),
                    new DungeonCell(
                            new DungeonCoord(1, 1),
                            new DungeonElement("BBB", "B", true)));
        }};

        final var map = new DungeonMap(2, 3, source, null);

        final var expected = List.of(
                new ElementDto("AAA", "A", new CoordinateDto(0, 0)),
                new ElementDto("BBB", "B", new CoordinateDto(1, 1))
        );

        final var actual = mapper.toDto(map);

        assertThat(actual.width(), equalTo(2));
        assertThat(actual.height(), equalTo(3));
        assertThat(
                actual.elements(),
                containsInAnyOrder(expected.toArray()));
    }
}
