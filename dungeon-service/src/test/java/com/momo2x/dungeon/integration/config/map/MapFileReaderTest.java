package com.momo2x.dungeon.integration.config.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.momo2x.dungeon.config.DungeonProperties;
import com.momo2x.dungeon.config.map.MapFileReader;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.*;


class MapFileReaderTest {

    private static final List<String> RAW_MAP = List.of(
            "[###][###][###][###]",
            "[###][   ][   ][###]",
            "[###][###][###][###]");

    private static final String RAW_JSON = """
            {
              "id": {
                "type": "type",
                "blocker": true,
                "avatar": "avatar",
                "direction": "direction",
                "bounce": "bounce"
              }
            }""";
    private static final DungeonProperties PROPS = mock(DungeonProperties.class);

    @BeforeEach
    void mockProperties() {
        when(PROPS.getMapFile()).thenReturn("/data/test.map");
        when(PROPS.getDataFile()).thenReturn("/data/test.json");
    }

    @AfterEach
    void resetMocks() {
        reset(PROPS);
    }

    @Test
    void readMapCoords() throws IOException {
        assertThat(
                new MapFileReader(PROPS).readMapCoords(),
                containsInAnyOrder(RAW_MAP.toArray()));
    }

    @Test
    void readMapElements() throws IOException {
        final var mapper = new ObjectMapper();

        assertThat(
                mapper.readTree(new MapFileReader(PROPS).readMapElements()),
                Matchers.equalTo(mapper.readTree(RAW_JSON)));
    }
}