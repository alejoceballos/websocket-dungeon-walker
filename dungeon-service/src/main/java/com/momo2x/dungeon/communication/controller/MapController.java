package com.momo2x.dungeon.communication.controller;

import com.momo2x.dungeon.communication.model.MapDto;
import com.momo2x.dungeon.communication.model.MapMapper;
import com.momo2x.dungeon.communication.service.DungeonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/map")
public class MapController {

    private final DungeonService service;

    private final MapMapper mapper;

    @GetMapping
    public MapDto getMap() {
        return mapper.toDto(service.getMap());
    }

}
