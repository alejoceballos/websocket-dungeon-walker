package com.momo2x.dungeon.unit.config.map;

import com.momo2x.dungeon.config.map.CatalogueItem;
import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.actors.DungeonWall;
import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.movement.MovementManager;

import java.util.*;

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

    public static final List<String> RAW_MAP = List.of(
            "[###][###][###][###][###][###]",
            "[###][   ][   ][   ][   ][###]",
            "[###][###][001][   ][   ][###]",
            "[###][   ][   ][   ][   ][###]",
            "[###][###][###][###][###][###]");

    public static final Map<String, List<DungeonCoord>> LOADED_MAP = Map.of(
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
                    "bounce": "simple"
                  }
                }
            """;

    public static final Map<String, CatalogueItem> LOADED_CATALOGUE = Map.of(
            "   ", new CatalogueItem("empty", false, null, null, null),
            "###", new CatalogueItem("wall", true, "#", null, null),
            "001", new CatalogueItem("walker", true, "A", "NE", "simple")
    );

    public static final DungeonWall DUNGEON_WALL = new DungeonWall("###", "#", true);

    public static final DungeonWalker DUNGEON_WALKER = new DungeonWalker("001", "1", true, NE, SIMPLE);

    public static final Map<DungeonCoord, DungeonCell> MAP_CELLS = new HashMap<>() {{
        put(new DungeonCoord(0, 0), new DungeonCell(new DungeonCoord(0, 0), DUNGEON_WALL));
        put(new DungeonCoord(1, 0), new DungeonCell(new DungeonCoord(1, 0), DUNGEON_WALL));
        put(new DungeonCoord(2, 0), new DungeonCell(new DungeonCoord(2, 0), DUNGEON_WALL));
        put(new DungeonCoord(3, 0), new DungeonCell(new DungeonCoord(3, 0), DUNGEON_WALL));
        put(new DungeonCoord(4, 0), new DungeonCell(new DungeonCoord(4, 0), DUNGEON_WALL));
        put(new DungeonCoord(5, 0), new DungeonCell(new DungeonCoord(5, 0), DUNGEON_WALL));

        put(new DungeonCoord(0, 1), new DungeonCell(new DungeonCoord(0, 1), DUNGEON_WALL));
        put(new DungeonCoord(1, 1), new DungeonCell(new DungeonCoord(1, 1), null));
        put(new DungeonCoord(2, 1), new DungeonCell(new DungeonCoord(2, 1), null));
        put(new DungeonCoord(3, 1), new DungeonCell(new DungeonCoord(3, 1), null));
        put(new DungeonCoord(4, 1), new DungeonCell(new DungeonCoord(4, 1), null));
        put(new DungeonCoord(5, 1), new DungeonCell(new DungeonCoord(5, 1), DUNGEON_WALL));

        put(new DungeonCoord(0, 2), new DungeonCell(new DungeonCoord(0, 2), DUNGEON_WALL));
        put(new DungeonCoord(1, 2), new DungeonCell(new DungeonCoord(1, 2), DUNGEON_WALL));
        put(new DungeonCoord(2, 2), new DungeonCell(new DungeonCoord(2, 2), DUNGEON_WALKER));
        put(new DungeonCoord(3, 2), new DungeonCell(new DungeonCoord(3, 2), null));
        put(new DungeonCoord(4, 2), new DungeonCell(new DungeonCoord(4, 2), null));
        put(new DungeonCoord(5, 2), new DungeonCell(new DungeonCoord(5, 2), DUNGEON_WALL));

        put(new DungeonCoord(0, 3), new DungeonCell(new DungeonCoord(0, 3), DUNGEON_WALL));
        put(new DungeonCoord(1, 3), new DungeonCell(new DungeonCoord(1, 3), null));
        put(new DungeonCoord(2, 3), new DungeonCell(new DungeonCoord(2, 3), null));
        put(new DungeonCoord(3, 3), new DungeonCell(new DungeonCoord(3, 3), null));
        put(new DungeonCoord(4, 3), new DungeonCell(new DungeonCoord(4, 3), null));
        put(new DungeonCoord(5, 3), new DungeonCell(new DungeonCoord(5, 3), DUNGEON_WALL));

        put(new DungeonCoord(0, 4), new DungeonCell(new DungeonCoord(0, 4), DUNGEON_WALL));
        put(new DungeonCoord(1, 4), new DungeonCell(new DungeonCoord(1, 4), DUNGEON_WALL));
        put(new DungeonCoord(2, 4), new DungeonCell(new DungeonCoord(2, 4), DUNGEON_WALL));
        put(new DungeonCoord(3, 4), new DungeonCell(new DungeonCoord(3, 4), DUNGEON_WALL));
        put(new DungeonCoord(4, 4), new DungeonCell(new DungeonCoord(4, 4), DUNGEON_WALL));
        put(new DungeonCoord(5, 4), new DungeonCell(new DungeonCoord(5, 4), DUNGEON_WALL));
    }};

    public static final Set<DungeonWall> MAP_WALLS = Set.of(DUNGEON_WALL);

    public static final Set<DungeonWalker> MAP_WALKERS = Set.of(DUNGEON_WALKER);

}
