package com.momo2x.dungeon.communication.model;

import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.map.DungeonCell;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.map.DungeonMap;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface MapMapper {

    @Named("mapToElements")
    static Set<ElementDto> mapToElements(Map<DungeonCoord, DungeonCell> map) {
        final var elements = new HashSet<ElementDto>();

        for (var mapEntry : map.entrySet()) {
            final var coord = mapEntry.getKey();
            final var element = mapEntry.getValue().getElement();

            if (element != null) {
                elements.add(
                        new ElementDto(
                                element.getId(),
                                element.getAvatar(),
                                (element instanceof DungeonWalker walker) ? walker.getSpeed() : 0,
                                new CoordinateDto(coord.x(), coord.y())));
            }
        }

        return elements;
    }

    @Mapping(source = "map", target = "elements", qualifiedByName = "mapToElements")
    MapDto toDto(DungeonMap map);

}
