package com.momo2x.dungeon.communication.model;

public record ElementDto(
        String id,
        String avatar,
        int speed,
        CoordinateDto coord
) {
}
