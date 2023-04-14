package com.momo2x.dungeon.config.map;

import com.momo2x.dungeon.engine.map.DungeonCoord;

import java.util.List;
import java.util.Map;

public record MapMetadata(
        Map<String, List<DungeonCoord>> elementsCoords,
        Map<String, Integer> elementsLayers) {
}
