package com.momo2x.dungeon.engine.movement;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public enum DirectionType {

    N("North", "S"),
    NE("Northeast", "SW"),
    E("East", "W"),
    SE("Southeast", "NW"),
    S("South", "N"),
    SW("Southwest", "NE"),
    W("West", "E"),
    NW("Northwest", "SE");

    public static final List<DirectionType> NORTHERN = List.of(N, NE, NW);
    public static final List<DirectionType> SOUTHERN = List.of(S, SE, SW);
    public static final List<DirectionType> EASTERN = List.of(E, SE, NE);
    public static final List<DirectionType> WESTERN = List.of(W, SW, NW);

    private final String description;

    private final String opposite;

    public DirectionType getOpposite() {
        return DirectionType.valueOf(this.opposite);
    }

    @Override
    public String toString() {
        return this.description;
    }

}
