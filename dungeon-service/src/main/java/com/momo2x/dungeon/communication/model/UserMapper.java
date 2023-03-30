package com.momo2x.dungeon.communication.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.userdetails.UserDetails;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "username", target = "id")
    UserDto toDto(UserDetails userDetails);

}
