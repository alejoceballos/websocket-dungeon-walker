package com.momo2x.dungeon.communication.model;

public record WalkerDto(
        String id,
        CoordinateDto previous,
        CoordinateDto current) {
}
