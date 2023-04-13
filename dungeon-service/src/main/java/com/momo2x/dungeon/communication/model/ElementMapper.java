package com.momo2x.dungeon.communication.model;

import com.momo2x.dungeon.engine.actors.DungeonElement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ElementMapper {

    @Mappings({
            @Mapping(source = "cell.coord", target = "coord")
    })
    ElementDto toDto(final DungeonElement element);

}
