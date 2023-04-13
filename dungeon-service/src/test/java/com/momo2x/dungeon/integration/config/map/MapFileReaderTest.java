package com.momo2x.dungeon.integration.config.map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.momo2x.dungeon.config.DungeonProperties;
import com.momo2x.dungeon.config.map.DataFileReaderException;
import com.momo2x.dungeon.config.map.MapFileReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;


class MapFileReaderTest {

    private static final List<LinkedList<String>> EXPECTED_RAW_MAPS = List.of(
            new LinkedList<>() {{
                add("        0    1    2    3");
                add("   0 [W01][W01][W01][W01]");
                add("   1 [W01][   ][   ][W01]");
                add("   2 [W01][   ][   ][W01]");
                add("   3 [W01][   ][   ][W01]");
                add("   4 [W01][W01][W01][W01]");
            }},
            new LinkedList<>() {{
                add("        0    1    2    3");
                add("   0 [###][###][###][###]");
                add("   1 [###][G01][E01][###]");
                add("   2 [###][D01][G01][###]");
                add("   3 [###][D01][D01][###]");
                add("   4 [###][###][###][###]");
            }},
            new LinkedList<>() {{
                add("        0    1    2    3");
                add("   0 [###][###][###][###]");
                add("   1 [###][   ][   ][###]");
                add("   2 [###][   ][   ][###]");
                add("   3 [###][001][   ][###]");
                add("   4 [###][###][###][###]");
            }});

    private static final String EXPECTED_RAW_JSON = """
              {
                "E01": {
                  "type": "walkable"
                },
                "W01": {
                  "type": "blocking",
                  "blocker": true,
                  "avatar": "wall"
                },
                "D01": {
                  "type": "walkable",
                  "blocker": false,
                  "avatar": "sand"
                },
                "G01": {
                  "type": "walkable",
                  "blocker": false,
                  "avatar": "grass"
                },
                "001": {
                  "type": "walker",
                  "blocker": true,
                  "avatar": "skull",
                  "direction": "NE",
                  "speed": 5,
                  "bounce": "simple"
                }
              }
            }""";

    private static final DungeonProperties PROPS = mock(DungeonProperties.class);

    @BeforeEach
    void mockProperties() {
        when(PROPS.getMapFilesPrefix()).thenReturn("/data/test");
        when(PROPS.getDataFilePrefix()).thenReturn("/data/catalogue-test");
    }

    @AfterEach
    void resetMocks() {
        reset(PROPS);
    }

    @Test
    void readMaps() throws DataFileReaderException {
        final var rawMaps = new MapFileReader(PROPS).readMaps();

        assertThat(3, equalTo(rawMaps.size()));
        assertThat(rawMaps.get(0), containsInAnyOrder(EXPECTED_RAW_MAPS.get(0).toArray()));
        assertThat(rawMaps.get(1), containsInAnyOrder(EXPECTED_RAW_MAPS.get(1).toArray()));
        assertThat(rawMaps.get(2), containsInAnyOrder(EXPECTED_RAW_MAPS.get(2).toArray()));
    }

    @Test
    void readElementsCatalogue() throws DataFileReaderException, JsonProcessingException {
        final var mapper = new ObjectMapper();

        assertThat(
                mapper.readTree(new MapFileReader(PROPS).readElementsCatalogue()),
                equalTo(mapper.readTree(EXPECTED_RAW_JSON)));
    }
}