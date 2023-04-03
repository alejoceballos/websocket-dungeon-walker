package com.momo2x.dungeon.communication.controller.in;

import com.momo2x.dungeon.DungeonException;
import com.momo2x.dungeon.communication.model.MovementDto;
import com.momo2x.dungeon.communication.service.PlayerService;
import com.momo2x.dungeon.engine.movement.DirectionType;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService service;

    @MessageMapping("/move")
    public void move(final MovementDto movementDto, final Authentication auth) {
        if (auth == null) {
            throw new RuntimeException("No authenticated user");
        }

        if (movementDto == null || movementDto.direction() == null || movementDto.direction().isBlank()) {
            throw new RuntimeException("No direction to go");
        }

        try {
            final var user = (UserDetails) auth.getPrincipal();

            service.move(
                    user.getUsername(),
                    DirectionType.valueOf(movementDto.direction().toUpperCase()));

        } catch (DungeonException e) {
            throw new RuntimeException(e);
        }
    }

}
