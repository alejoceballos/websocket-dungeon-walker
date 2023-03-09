package com.momo2x.dungeon.communication.model;

public record WalkerDto(
        String id,
        String avatar,
        CoordinateDto previous,
        CoordinateDto current) {
}
