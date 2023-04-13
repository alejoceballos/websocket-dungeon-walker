package com.momo2x.dungeon.engine.map;

import com.momo2x.dungeon.engine.actors.DungeonElement;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;

@Builder
public class DungeonCell {

    @Getter
    private final DungeonCoord coord;

    private final Stack<DungeonElement> elements = new Stack<>();

    public DungeonCell(DungeonCoord coord) {
        this.coord = coord;
    }

    public DungeonCell(DungeonCoord coord, DungeonElement... elements) {
        this.coord = coord;
        Arrays.stream(elements).forEach(this.elements::push);
    }

    public DungeonCell(DungeonCoord coord, Stack<DungeonElement> elements) {
        this.coord = coord;
        this.elements.addAll(elements);
    }

    public DungeonElement getTopElement() {
        return this.elements.isEmpty() ? null : this.elements.peek();
    }

    public void addElementToTop(final DungeonElement element) {
        this.elements.push(element);
    }

    public void removeTopElement() {
        this.elements.pop();
    }

    public boolean isBlocked() {
        return this.elements.stream().anyMatch(DungeonElement::isBlocker);
    }

    @Override
    public String toString() {
        return "Cell " +
                (this.coord == null ? "(?,?)" : this.coord) +
                ": " +
                (this.elements.isEmpty()
                        ? "empty"
                        : this.elements.stream()
                        .map(DungeonElement::getId)
                        .reduce("", (result, current) -> result.isBlank()
                                ? current
                                : result + ", " + current));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DungeonCell that = (DungeonCell) o;
        return this.coord.equals(that.coord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.coord);
    }
}
