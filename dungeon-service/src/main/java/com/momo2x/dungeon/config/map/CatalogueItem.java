package com.momo2x.dungeon.config.map;

public record CatalogueItem(
        String type,
        boolean blocker,
        String avatar,
        String direction,
        String bounce) {
}
