package com.momo2x.dungeon.communication.model;

import com.momo2x.dungeon.engine.actors.DungeonWalker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface WalkerMapper {

    @Mappings({
            @Mapping(source = "previousCell.coord", target = "previous"),
            @Mapping(source = "cell.coord", target = "current")
    })
    WalkerDto toDto(final DungeonWalker walker);

}
