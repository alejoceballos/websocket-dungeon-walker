package com.momo2x.dungeon.communication.model;

import java.util.Arrays;

public record MapUpdateDto(ElementDto... elements) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapUpdateDto that = (MapUpdateDto) o;
        return Arrays.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }

    @Override
    public String toString() {
        return "MapUpdateDto{" +
                "elements=" + Arrays.toString(elements) +
                '}';
    }
}
