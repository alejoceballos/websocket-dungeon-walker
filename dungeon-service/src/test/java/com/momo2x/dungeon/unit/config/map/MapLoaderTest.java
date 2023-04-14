package com.momo2x.dungeon.unit.config.map;

import com.momo2x.dungeon.config.map.MalformedMapException;
import com.momo2x.dungeon.config.map.MapLoader;
import org.junit.jupiter.api.Test;

import static com.momo2x.dungeon.unit.MapTestUtil.mockMapMetadata;
import static com.momo2x.dungeon.unit.MapTestUtil.mockRawMaps;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class MapLoaderTest {

    @Test
    void load() throws MalformedMapException {
        final var actual = new MapLoader(mockRawMaps.get()).load();
        final var expected = mockMapMetadata.get();

        assertThat(actual.elementsCoords().keySet(), equalTo(expected.elementsCoords().keySet()));

        for (final var expectedEntry : expected.elementsCoords().entrySet()) {
            assertThat(
                    "Error on key %s".formatted(expectedEntry.getKey()),
                    actual.elementsCoords().get(expectedEntry.getKey()),
                    equalTo(expectedEntry.getValue()));
        }

        assertThat(actual.elementsLayers().keySet(), equalTo(expected.elementsLayers().keySet()));

        for (final var expectedEntry : expected.elementsLayers().entrySet()) {
            assertThat(
                    "Error on key %s".formatted(expectedEntry.getKey()),
                    actual.elementsLayers().get(expectedEntry.getKey()),
                    equalTo(expectedEntry.getValue()));
        }
    }

}