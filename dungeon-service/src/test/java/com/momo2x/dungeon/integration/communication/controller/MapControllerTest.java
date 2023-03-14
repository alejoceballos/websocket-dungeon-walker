package com.momo2x.dungeon.integration.communication.controller;

import org.junit.jupiter.api.Test;

//@WebMvcTest
//@ComponentScan(basePackageClasses = {MapMapperImpl.class})
class MapControllerTest {
    //
//    private static final DungeonMap MOCKED_MAP = new DungeonMap(
//            2,
//            2,
//            Map.of(
//                    new DungeonCoord(0, 0),
//                    new DungeonCell(new DungeonCoord(0, 0), new DungeonWall("001", "1", true)),
//                    new DungeonCoord(1, 0),
//                    new DungeonCell(new DungeonCoord(1, 0), new DungeonWall("002", "2", true)),
//                    new DungeonCoord(0, 1),
//                    new DungeonCell(new DungeonCoord(0, 1), new DungeonWall("003", "3", true)),
//                    new DungeonCoord(1, 1),
//                    new DungeonCell(new DungeonCoord(1, 1), new DungeonWall("004", "4", true))
//            ),
//            null,
//            null);
//
//    private static final String MAP_OUTPUT_CONTENT = """
//                {
//                    "width": 2,
//                    "height": 2,
//                    "elements": [
//                        {
//                            "id": "001",
//                            "avatar": "1",
//                            "coord": {
//                                "x": 0,
//                                "y": 0
//                            }
//                        },
//                        {
//                            "id": "002",
//                            "avatar": "2",
//                            "coord": {
//                                "x": 1,
//                                "y": 0
//                            }
//                        },
//                        {
//                            "id": "003",
//                            "avatar": "3",
//                            "coord": {
//                                "x": 0,
//                                "y": 1
//                            }
//                        },
//                        {
//                            "id": "004",
//                            "avatar": "4",
//                            "coord": {
//                                "x": 1,
//                                "y": 1
//                            }
//                        }
//                    ]
//                }
//            """;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private DungeonService service;
//
//    @MockBean
//    private UserDetailsService userDetailsService;
//
//    @MockBean
//    private SecurityFilterChain securityFilterChain;
//
//    @SpyBean
//    private MapMapper mapper;
//
//    @SpyBean
//    private DungeonMap map;
//
//    @SpyBean
//    private PlayerService playerService;
//
//    @BeforeEach
//    void mockService() {
//        when(service.getMap()).thenReturn(MOCKED_MAP);
//    }
//
//    @AfterEach
//    void clearMocks() {
//        reset(service, userDetailsService, securityFilterChain, mapper);
//    }
//
    @Test
    void getMap() throws Exception {
//        this.mockMvc.perform(
//                        get("/v1/dungeon/map")
//                                .with(httpBasic("test", "test")))
//                .andExpectAll(
//                        status().isOk(),
//                        content().contentType(APPLICATION_JSON_VALUE),
//                        content().json(MAP_OUTPUT_CONTENT));
    }
}