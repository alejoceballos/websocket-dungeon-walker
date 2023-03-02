package com.momo2x.dungeon.comm.model;

import com.momo2x.dungeon.engine.map.DungeonCoord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoordinateMapper {

    CoordinateDto toDto(DungeonCoord coord);

}
