package com.momo2x.dungeon.communication.model;

import com.momo2x.dungeon.engine.map.DungeonMap;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapMapper {

    MapDto toDto(DungeonMap map);

}
