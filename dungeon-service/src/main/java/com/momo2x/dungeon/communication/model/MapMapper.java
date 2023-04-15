package com.momo2x.dungeon.communication.model;

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

        for (var cellByCoord : map.entrySet()) {
            cellByCoord.getValue().getElements().forEach(elem ->
                    elements.add(
                            new ElementDto(
                                    elem.getId(),
                                    elem.getAvatar(),
                                    new CoordinateDto(cellByCoord.getKey().x(), cellByCoord.getKey().y()),
                                    elem.getLayerIndex())));
        }

        return elements;
    }

    @Mapping(source = "map", target = "elements", qualifiedByName = "mapToElements")
    MapDto toDto(DungeonMap map);

}
