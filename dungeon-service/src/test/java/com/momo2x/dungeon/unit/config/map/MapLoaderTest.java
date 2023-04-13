package com.momo2x.dungeon.unit.config.map;

import com.momo2x.dungeon.config.map.MalformedMapException;
import com.momo2x.dungeon.config.map.MapLoader;
import org.junit.jupiter.api.Test;

import static com.momo2x.dungeon.unit.MapTestUtil.mockLoadedMap;
import static com.momo2x.dungeon.unit.MapTestUtil.mockRawMaps;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class MapLoaderTest {

    @Test
    void load() throws MalformedMapException {
        final var actual = new MapLoader(mockRawMaps.get()).load();
        final var expected = mockLoadedMap.get();

        assertThat(actual.keySet(), equalTo(expected.keySet()));

        for (final var expectedEntry : expected.entrySet()) {
            assertThat(
                    "Error on key %s".formatted(expectedEntry.getKey()),
                    actual.get(expectedEntry.getKey()),
                    equalTo(expectedEntry.getValue()));
        }
    }

}