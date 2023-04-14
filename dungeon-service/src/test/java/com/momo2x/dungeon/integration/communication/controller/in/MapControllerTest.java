package com.momo2x.dungeon.integration.communication.controller.in;

import com.momo2x.dungeon.communication.model.MapMapperImpl;
import com.momo2x.dungeon.engine.actors.DungeonElement;
import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.map.DungeonMap;
import com.momo2x.dungeon.integration.communication.controller.BaseIntegrationTestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ComponentScan(basePackageClasses = {MapMapperImpl.class})
class MapControllerTest extends BaseIntegrationTestController {

    private static final DungeonMap MOCKED_MAP = new DungeonMap(
            2,
            2,
            1,
            Map.of(
                    new DungeonCoord(0, 0),
                    new DungeonCell(new DungeonCoord(0, 0), new DungeonElement("W01", "1", true, 0)),
                    new DungeonCoord(1, 0),
                    new DungeonCell(new DungeonCoord(1, 0), new DungeonElement("W02", "2", true, 0)),
                    new DungeonCoord(0, 1),
                    new DungeonCell(new DungeonCoord(0, 1), new DungeonElement("W03", "3", true, 0)),
                    new DungeonCoord(1, 1),
                    new DungeonCell(new DungeonCoord(1, 1), new DungeonElement("W04", "4", true, 0))
            ),
            null);

    private static final String MAP_OUTPUT_CONTENT = """
                {
                    "width": 2,
                    "height": 2,
                    "elements": [
                        {
                            "id": "W01",
                            "avatar": "1",
                            "coord": {
                                "x": 0,
                                "y": 0
                            }
                        },
                        {
                            "id": "W02",
                            "avatar": "2",
                            "coord": {
                                "x": 1,
                                "y": 0
                            }
                        },
                        {
                            "id": "W03",
                            "avatar": "3",
                            "coord": {
                                "x": 0,
                                "y": 1
                            }
                        },
                        {
                            "id": "W04",
                            "avatar": "4",
                            "coord": {
                                "x": 1,
                                "y": 1
                            }
                        }
                    ]
                }
            """;

    @BeforeEach
    void mockService() {
        when(this.dungeonService.getMap()).thenReturn(MOCKED_MAP);
    }

    @Test
    void getMap() throws Exception {
        this.mockMvc.perform(
                        get("/v1/maps")
                                .with(httpBasic(USERNAME, PASSWORD)))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(APPLICATION_JSON_VALUE),
                        content().json(MAP_OUTPUT_CONTENT));
    }
}