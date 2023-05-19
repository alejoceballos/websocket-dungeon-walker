package com.momo2x.dungeon.unit;

import com.momo2x.dungeon.config.map.CatalogueItem;
import com.momo2x.dungeon.config.map.MapMetadata;
import com.momo2x.dungeon.engine.actors.DungeonAutonomousWalker;
import com.momo2x.dungeon.engine.actors.DungeonElement;
import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.map.DungeonMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.momo2x.dungeon.engine.interaction.bounce.BounceStrategyType.SIMPLE;
import static com.momo2x.dungeon.engine.movement.DirectionType.NE;

public class MapTestUtil {

    public static final Supplier<LinkedList<String>> mockRawMap00 = () -> new LinkedList<>() {{
        add("        0    1    2    3    4    5 ");
        add("   0 [W01][W01][W01][W01][W01][W01]");
        add("   1 [W01][   ][   ][   ][   ][W01]");
        add("   2 [W01][   ][   ][   ][   ][W01]");
        add("   3 [W01][   ][   ][   ][   ][W01]");
        add("   4 [W01][W01][W01][W01][W01][W01]");
    }};

    public static final Supplier<LinkedList<String>> mockRawMap01 = () -> new LinkedList<>() {{
        add("        0    1    2    3    4    5 ");
        add("   0 [###][###][###][###][###][###]");
        add("   1 [###][D01][D01][G01][G01][###]");
        add("   2 [###][D01][G01][G01][E01][###]");
        add("   3 [###][D01][D01][G01][G01][###]");
        add("   4 [###][###][###][###][###][###]");
    }};

    public static final Supplier<LinkedList<String>> mockRawMap02 = () -> new LinkedList<>() {{
        add("        0    1    2    3    4    5 ");
        add("   0 [###][###][###][###][###][###]");
        add("   1 [###][   ][   ][   ][   ][###]");
        add("   2 [###][   ][001][   ][   ][###]");
        add("   3 [###][   ][   ][   ][   ][###]");
        add("   4 [###][###][###][###][###][###]");
    }};

    public static final Supplier<List<LinkedList<String>>> mockRawMaps = () -> List.of(
            mockRawMap00.get(),
            mockRawMap01.get(),
            mockRawMap02.get());

    public static final Supplier<Map<String, List<DungeonCoord>>> mockMapElementsCoords = () -> Map.of(
            "E01", new ArrayList<>() {{
                // Line 3
                add(new DungeonCoord(4, 2));
            }},
            "W01", new ArrayList<>() {{
                // Line 1
                add(new DungeonCoord(0, 0));
                add(new DungeonCoord(1, 0));
                add(new DungeonCoord(2, 0));
                add(new DungeonCoord(3, 0));
                add(new DungeonCoord(4, 0));
                add(new DungeonCoord(5, 0));
                // Line 2
                add(new DungeonCoord(0, 1));
                add(new DungeonCoord(5, 1));
                // Line 3
                add(new DungeonCoord(0, 2));
                add(new DungeonCoord(5, 2));
                // Line 4
                add(new DungeonCoord(0, 3));
                add(new DungeonCoord(5, 3));
                // Line 5
                add(new DungeonCoord(0, 4));
                add(new DungeonCoord(1, 4));
                add(new DungeonCoord(2, 4));
                add(new DungeonCoord(3, 4));
                add(new DungeonCoord(4, 4));
                add(new DungeonCoord(5, 4));
            }},
            "D01", new ArrayList<>() {{
                // Line 2
                add(new DungeonCoord(1, 1));
                add(new DungeonCoord(2, 1));
                // Line 3
                add(new DungeonCoord(1, 2));
                // Line 4
                add(new DungeonCoord(1, 3));
                add(new DungeonCoord(2, 3));
            }},
            "G01", new ArrayList<>() {{
                // Line 2
                add(new DungeonCoord(3, 1));
                add(new DungeonCoord(4, 1));
                // Line 3
                add(new DungeonCoord(2, 2));
                add(new DungeonCoord(3, 2));
                // Line 4
                add(new DungeonCoord(3, 3));
                add(new DungeonCoord(4, 3));
            }},
            "001", new ArrayList<>() {{
                // Line 3
                add(new DungeonCoord(2, 2));
            }});

    public static final Supplier<Map<String, Integer>> mockElementsLayers = () -> Map.of(
            "E01", 1,
            "W01", 0,
            "D01", 1,
            "G01", 1,
            "001", 2);

    public static final Supplier<MapMetadata> mockMapMetadata = () -> new MapMetadata(
            mockMapElementsCoords.get(),
            mockElementsLayers.get()
    );

    public static final String RAW_CATALOGUE = """
                {
                  "E01": {
                    "type": "walkable",
                    "blocker": false
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
            """;

    public static final Supplier<Map<String, CatalogueItem>> mockUploadedCatalogue = () -> Map.of(
            "E01", new CatalogueItem("walkable", false, null, null, 0, null),
            "W01", new CatalogueItem("blocking", true, "wall", null, 0, null),
            "D01", new CatalogueItem("walkable", false, "sand", null, 0, null),
            "G01", new CatalogueItem("walkable", false, "grass", null, 0, null),
            "001", new CatalogueItem("walker", true, "skull", "NE", 5, "simple")
    );

    public static final Supplier<DungeonElement> mockDungeonWall = () -> new DungeonElement("W01", "wall", true, 0);

    public static final Supplier<DungeonElement> mockDungeonEmpty = () -> new DungeonElement("E01", null, false, 1);

    public static final Supplier<DungeonElement> mockDungeonDesert = () -> new DungeonElement("D01", "sand", false, 1);

    public static final Supplier<DungeonElement> mockDungeonGrass = () -> new DungeonElement("G01", "grass", false, 1);

    public static final Supplier<DungeonAutonomousWalker> mockDungeonAutoWalker = () ->
            new DungeonAutonomousWalker("001", "skull", true, 2, NE, 5, SIMPLE);

    public static final Supplier<Map<DungeonCoord, DungeonCell>> mockMapCells = () ->
            new HashMap<>() {{
                put(new DungeonCoord(0, 0), new DungeonCell(new DungeonCoord(0, 0), mockDungeonWall.get()));
                put(new DungeonCoord(1, 0), new DungeonCell(new DungeonCoord(1, 0), mockDungeonWall.get()));
                put(new DungeonCoord(2, 0), new DungeonCell(new DungeonCoord(2, 0), mockDungeonWall.get()));
                put(new DungeonCoord(3, 0), new DungeonCell(new DungeonCoord(3, 0), mockDungeonWall.get()));
                put(new DungeonCoord(4, 0), new DungeonCell(new DungeonCoord(4, 0), mockDungeonWall.get()));
                put(new DungeonCoord(5, 0), new DungeonCell(new DungeonCoord(5, 0), mockDungeonWall.get()));

                put(new DungeonCoord(0, 1), new DungeonCell(new DungeonCoord(0, 1), mockDungeonWall.get()));
                put(new DungeonCoord(1, 1), new DungeonCell(new DungeonCoord(1, 1), mockDungeonDesert.get()));
                put(new DungeonCoord(2, 1), new DungeonCell(new DungeonCoord(2, 1), mockDungeonDesert.get()));
                put(new DungeonCoord(3, 1), new DungeonCell(new DungeonCoord(3, 1), mockDungeonGrass.get()));
                put(new DungeonCoord(4, 1), new DungeonCell(new DungeonCoord(4, 1), mockDungeonGrass.get()));
                put(new DungeonCoord(5, 1), new DungeonCell(new DungeonCoord(5, 1), mockDungeonWall.get()));

                put(new DungeonCoord(0, 2), new DungeonCell(new DungeonCoord(0, 2), mockDungeonWall.get()));
                put(new DungeonCoord(1, 2), new DungeonCell(new DungeonCoord(1, 2), mockDungeonDesert.get()));
                put(new DungeonCoord(2, 2), new DungeonCell(
                        new DungeonCoord(2, 2), mockDungeonGrass.get(), mockDungeonAutoWalker.get()));
                put(new DungeonCoord(3, 2), new DungeonCell(new DungeonCoord(3, 2), mockDungeonGrass.get()));
                put(new DungeonCoord(4, 2), new DungeonCell(new DungeonCoord(4, 2), mockDungeonEmpty.get()));
                put(new DungeonCoord(5, 2), new DungeonCell(new DungeonCoord(5, 2), mockDungeonWall.get()));

                put(new DungeonCoord(0, 3), new DungeonCell(new DungeonCoord(0, 3), mockDungeonWall.get()));
                put(new DungeonCoord(1, 3), new DungeonCell(new DungeonCoord(1, 3), mockDungeonDesert.get()));
                put(new DungeonCoord(2, 3), new DungeonCell(new DungeonCoord(2, 3), mockDungeonDesert.get()));
                put(new DungeonCoord(3, 3), new DungeonCell(new DungeonCoord(3, 3), mockDungeonGrass.get()));
                put(new DungeonCoord(4, 3), new DungeonCell(new DungeonCoord(4, 3), mockDungeonGrass.get()));
                put(new DungeonCoord(5, 3), new DungeonCell(new DungeonCoord(5, 3), mockDungeonWall.get()));

                put(new DungeonCoord(0, 4), new DungeonCell(new DungeonCoord(0, 4), mockDungeonWall.get()));
                put(new DungeonCoord(1, 4), new DungeonCell(new DungeonCoord(1, 4), mockDungeonWall.get()));
                put(new DungeonCoord(2, 4), new DungeonCell(new DungeonCoord(2, 4), mockDungeonWall.get()));
                put(new DungeonCoord(3, 4), new DungeonCell(new DungeonCoord(3, 4), mockDungeonWall.get()));
                put(new DungeonCoord(4, 4), new DungeonCell(new DungeonCoord(4, 4), mockDungeonWall.get()));
                put(new DungeonCoord(5, 4), new DungeonCell(new DungeonCoord(5, 4), mockDungeonWall.get()));
            }};
    public static final Supplier<DungeonMap> mockDungeonMap = () -> {
        final var mapCellsMock = mockMapCells.get();

        final var dungeonAutoWalkerCell = mapCellsMock.get(new DungeonCoord(2, 2));
        final var dungeonAutoWalkerElement = (DungeonWalker) dungeonAutoWalkerCell.getTopElement();

        final var mapWalkersMock = new HashMap<String, DungeonWalker>() {{
            put(dungeonAutoWalkerElement.getId(), dungeonAutoWalkerElement);
        }};

        dungeonAutoWalkerElement.setCell(dungeonAutoWalkerCell);

        return new DungeonMap(6, 5, mockRawMaps.get().size() + 1, mapCellsMock, mapWalkersMock);
    };
    public static final Supplier<Map<String, DungeonWalker>> mockMapWalkers = () -> {
        final var dungeonAutoWalkerMock = mockDungeonAutoWalker.get();

        return Map.of(
                dungeonAutoWalkerMock.getId(),
                dungeonAutoWalkerMock);
    };

}
