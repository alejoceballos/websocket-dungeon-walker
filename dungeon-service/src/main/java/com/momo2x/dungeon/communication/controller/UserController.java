package com.momo2x.dungeon.communication.controller;

import com.momo2x.dungeon.communication.model.UserDto;
import com.momo2x.dungeon.communication.model.UserMapper;
import com.momo2x.dungeon.communication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final UserService service;

    private final UserMapper mapper;

    @GetMapping("/current")
    public UserDto getCurrent() {
        return mapper.toDto(service.getLoggedUser());
    }

}
