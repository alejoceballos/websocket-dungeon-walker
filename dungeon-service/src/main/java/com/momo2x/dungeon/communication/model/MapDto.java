package com.momo2x.dungeon.communication.model;

import com.momo2x.dungeon.engine.map.DungeonCoord;
import lombok.Builder;

import java.util.Set;

@Builder
public record MapDto(int width, int height, Set<DungeonCoord> walls) {
}
