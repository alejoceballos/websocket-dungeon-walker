package com.momo2x.dungeon.integration.communication.model;

import com.momo2x.dungeon.communication.model.CoordinateDto;
import com.momo2x.dungeon.communication.model.ElementDto;
import com.momo2x.dungeon.communication.model.MapMapper;
import com.momo2x.dungeon.communication.model.MapMapperImpl;
import com.momo2x.dungeon.engine.actors.DungeonWall;
import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.map.DungeonMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MapMapperImpl.class})
public class MapMapperTest {

    @Autowired
    private MapMapper mapper;

    @Test
    void toDto() {
        final var source = new HashMap<DungeonCoord, DungeonCell>() {{
            put(
                    new DungeonCoord(0, 0),
                    new DungeonCell(
                            new DungeonCoord(0, 0),
                            new DungeonWall("AAA", "A", true)));
            put(
                    new DungeonCoord(1, 1),
                    new DungeonCell(
                            new DungeonCoord(1, 1),
                            new DungeonWall("BBB", "B", true)));
        }};

        final var map = new DungeonMap(2, 3, source, null, null);

        final var expected = List.of(
                new ElementDto("AAA", "A", 5, new CoordinateDto(0, 0)),
                new ElementDto("BBB", "B", 6, new CoordinateDto(1, 1))
        );

        final var actual = mapper.toDto(map);

        assertThat(actual.width(), equalTo(2));
        assertThat(actual.height(), equalTo(3));
        assertThat(
                actual.elements(),
                containsInAnyOrder(expected.toArray()));
    }
}
