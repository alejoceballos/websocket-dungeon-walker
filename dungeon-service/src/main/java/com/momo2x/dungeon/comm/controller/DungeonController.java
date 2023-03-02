package com.momo2x.dungeon.comm.controller;

import com.momo2x.dungeon.comm.model.MapMapper;
import com.momo2x.dungeon.comm.model.MapDto;
import com.momo2x.dungeon.comm.service.DungeonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/dungeon")
public class DungeonController {

    private final DungeonService service;

    private final MapMapper mapper;

    @GetMapping("/map")
    public MapDto getMap() {
        return mapper.toDto(service.getMap());
    }

}
