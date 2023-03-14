package com.momo2x.dungeon.unit;

import com.momo2x.dungeon.config.map.CatalogueItem;
import com.momo2x.dungeon.engine.actors.DungeonAutonomousWalker;
import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.actors.DungeonWall;
import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.map.DungeonMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.momo2x.dungeon.engine.movement.BounceStrategyType.SIMPLE;
import static com.momo2x.dungeon.engine.movement.DirectionType.NE;

public class MapTestUtil {

    /*
    +-----+-----+-----+-----+-----+-----+
    | 0,0 | 1,0 | 2,0 | 3,0 | 4,0 | 5,0 |
    +-----+-----+-----+-----+-----+-----+
    | 0,1 | 1,1 | 2,1 | 3,1 | 4,1 | 5,1 |
    +-----+-----+-----+-----+-----+-----+
    | 0,2 | 1,2 | 2,2 | 3,2 | 4,2 | 5,2 |
    +-----+-----+-----+-----+-----+-----+
    | 0,3 | 1,3 | 2,3 | 3,3 | 4,3 | 5,3 |
    +-----+-----+-----+-----+-----+-----+
    | 0,4 | 1,4 | 2,4 | 3,4 | 4,4 | 5,4 |
    +-----+-----+-----+-----+-----+-----+
    */

    public static final Supplier<List<String>> mockRawMap = () -> List.of(
            "[###][###][###][###][###][###]",
            "[###][   ][   ][   ][   ][###]",
            "[###][###][001][   ][   ][###]",
            "[###][   ][   ][   ][   ][###]",
            "[###][###][###][###][###][###]");

    public static final Supplier<Map<String, List<DungeonCoord>>> mockLoadedMap = () -> Map.of(
            "   ", new ArrayList<>() {{
                // Line 2
                add(new DungeonCoord(1, 1));
                add(new DungeonCoord(2, 1));
                add(new DungeonCoord(3, 1));
                add(new DungeonCoord(4, 1));
                // Line 3
                add(new DungeonCoord(3, 2));
                add(new DungeonCoord(4, 2));
                // Line 4
                add(new DungeonCoord(1, 3));
                add(new DungeonCoord(2, 3));
                add(new DungeonCoord(3, 3));
                add(new DungeonCoord(4, 3));
            }},
            "###", new ArrayList<>() {{
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
                add(new DungeonCoord(1, 2));
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
            "001", new ArrayList<>() {{
                // Line 3
                add(new DungeonCoord(2, 2));
            }});

    public static final String RAW_CATALOGUE = """
                {
                  "   ": {
                    "type": "empty"
                  },
                  "###": {
                    "type": "wall",
                    "blocker": true,
                    "avatar": "#"
                  },
                  "001": {
                    "type": "walker",
                    "blocker": true,
                    "avatar": "A",
                    "direction": "NE",
                    "speed": 5,
                    "bounce": "simple"
                  }
                }
            """;

    public static final Supplier<Map<String, CatalogueItem>> mockUploadedCatalogue = () -> Map.of(
            "   ", new CatalogueItem("empty", false, null, null, 0, null),
            "###", new CatalogueItem("wall", true, "#", null, 0, null),
            "001", new CatalogueItem("walker", true, "A", "NE", 5, "simple")
    );

    public static final Supplier<DungeonWall> mockDungeonWall = () -> new DungeonWall("###", "#", true);

    public static final Supplier<DungeonAutonomousWalker> mockDungeonAutoWalker = () ->
            new DungeonAutonomousWalker("001", "1", true, NE, 5, SIMPLE);

    public static final Supplier<Map<DungeonCoord, DungeonCell>> mockMapCells = () -> {
        final var dungeonWallMock = mockDungeonWall.get();

        return new HashMap<>() {{
            put(new DungeonCoord(0, 0), new DungeonCell(new DungeonCoord(0, 0), dungeonWallMock));
            put(new DungeonCoord(1, 0), new DungeonCell(new DungeonCoord(1, 0), dungeonWallMock));
            put(new DungeonCoord(2, 0), new DungeonCell(new DungeonCoord(2, 0), dungeonWallMock));
            put(new DungeonCoord(3, 0), new DungeonCell(new DungeonCoord(3, 0), dungeonWallMock));
            put(new DungeonCoord(4, 0), new DungeonCell(new DungeonCoord(4, 0), dungeonWallMock));
            put(new DungeonCoord(5, 0), new DungeonCell(new DungeonCoord(5, 0), dungeonWallMock));

            put(new DungeonCoord(0, 1), new DungeonCell(new DungeonCoord(0, 1), dungeonWallMock));
            put(new DungeonCoord(1, 1), new DungeonCell(new DungeonCoord(1, 1), null));
            put(new DungeonCoord(2, 1), new DungeonCell(new DungeonCoord(2, 1), null));
            put(new DungeonCoord(3, 1), new DungeonCell(new DungeonCoord(3, 1), null));
            put(new DungeonCoord(4, 1), new DungeonCell(new DungeonCoord(4, 1), null));
            put(new DungeonCoord(5, 1), new DungeonCell(new DungeonCoord(5, 1), dungeonWallMock));

            put(new DungeonCoord(0, 2), new DungeonCell(new DungeonCoord(0, 2), dungeonWallMock));
            put(new DungeonCoord(1, 2), new DungeonCell(new DungeonCoord(1, 2), dungeonWallMock));
            put(new DungeonCoord(2, 2), new DungeonCell(new DungeonCoord(2, 2), mockDungeonAutoWalker.get()));
            put(new DungeonCoord(3, 2), new DungeonCell(new DungeonCoord(3, 2), null));
            put(new DungeonCoord(4, 2), new DungeonCell(new DungeonCoord(4, 2), null));
            put(new DungeonCoord(5, 2), new DungeonCell(new DungeonCoord(5, 2), dungeonWallMock));

            put(new DungeonCoord(0, 3), new DungeonCell(new DungeonCoord(0, 3), dungeonWallMock));
            put(new DungeonCoord(1, 3), new DungeonCell(new DungeonCoord(1, 3), null));
            put(new DungeonCoord(2, 3), new DungeonCell(new DungeonCoord(2, 3), null));
            put(new DungeonCoord(3, 3), new DungeonCell(new DungeonCoord(3, 3), null));
            put(new DungeonCoord(4, 3), new DungeonCell(new DungeonCoord(4, 3), null));
            put(new DungeonCoord(5, 3), new DungeonCell(new DungeonCoord(5, 3), dungeonWallMock));

            put(new DungeonCoord(0, 4), new DungeonCell(new DungeonCoord(0, 4), dungeonWallMock));
            put(new DungeonCoord(1, 4), new DungeonCell(new DungeonCoord(1, 4), dungeonWallMock));
            put(new DungeonCoord(2, 4), new DungeonCell(new DungeonCoord(2, 4), dungeonWallMock));
            put(new DungeonCoord(3, 4), new DungeonCell(new DungeonCoord(3, 4), dungeonWallMock));
            put(new DungeonCoord(4, 4), new DungeonCell(new DungeonCoord(4, 4), dungeonWallMock));
            put(new DungeonCoord(5, 4), new DungeonCell(new DungeonCoord(5, 4), dungeonWallMock));
        }};
    };
    public static final Supplier<DungeonMap> mockDungeonMap = () -> {
        final var mapCellsMock = mockMapCells.get();

        final var dungeonAutoWalkerCell = mapCellsMock.get(new DungeonCoord(2, 2));

        final var mapWalkersMock = new HashMap<String, DungeonWalker>() {{
            put(dungeonAutoWalkerCell.getElement().getId(), (DungeonWalker) dungeonAutoWalkerCell.getElement());
        }};

        final var mapWallsMock = mapCellsMock
                .values()
                .stream()
                .filter(cell -> cell.getElement() instanceof DungeonWall)
                .map(cell -> (DungeonWall) cell.getElement())
                .collect(Collectors.toSet());

        dungeonAutoWalkerCell.getElement().setCell(dungeonAutoWalkerCell);

        return new DungeonMap(36, 36, mapCellsMock, mapWallsMock, mapWalkersMock);
    };
    public static final Supplier<Set<DungeonWall>> mockMapWalls = () -> Set.of(mockDungeonWall.get());
    public static final Supplier<Map<String, DungeonWalker>> mockMapWalkers = () -> {
        final var dungeonAutoWalkerMock = mockDungeonAutoWalker.get();

        return Map.of(
                dungeonAutoWalkerMock.getId(),
                dungeonAutoWalkerMock);
    };

}
