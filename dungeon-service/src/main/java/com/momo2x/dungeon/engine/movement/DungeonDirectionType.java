package com.momo2x.dungeon.engine.movement;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public enum DungeonDirectionType {
    N("North", "S"),
    S("South", "N"),
    E("East", "W"),
    W("West", "E"),
    NE("Northeast", "SW"),
    NW("Northwest", "SE"),
    SE("Southeast", "NW"),
    SW("Southwest", "NE");

    public static final List<DungeonDirectionType> NORTHERN = List.of(N, NE, NW);
    public static final List<DungeonDirectionType> SOUTHERN = List.of(S, SE, SW);
    public static final List<DungeonDirectionType> EASTERN = List.of(E, SE, NE);
    public static final List<DungeonDirectionType> WESTERN = List.of(W, SW, NW);

    private final String description;

    private final String opposite;

    public DungeonDirectionType getOpposite() {
        return DungeonDirectionType.valueOf(opposite);
    }

    @Override
    public String toString() {
        return this.description;
    }
}
