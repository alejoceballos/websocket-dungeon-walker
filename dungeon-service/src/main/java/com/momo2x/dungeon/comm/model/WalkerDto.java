package com.momo2x.dungeon.comm.model;

public record WalkerDto(
        String id,
        CoordinateDto previous,
        CoordinateDto current) {
}
