package com.momo2x.dungeon.comm.model;

import com.momo2x.dungeon.config.DungeonProperties;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapMapper {

    MapDto toDto(DungeonProperties dungeon);

}
