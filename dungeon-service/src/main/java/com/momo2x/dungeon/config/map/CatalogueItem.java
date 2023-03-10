package com.momo2x.dungeon.config.map;

public record CatalogueItem(
        String type,
        boolean blocker,
        String avatar,
        String direction,
        int speed,
        String bounce) {
}
