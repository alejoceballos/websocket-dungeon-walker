package com.momo2x.dungeon.communication.model;

import com.momo2x.dungeon.engine.map.DungeonCoord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoordinateMapper {

    CoordinateDto toDto(DungeonCoord coord);

}
