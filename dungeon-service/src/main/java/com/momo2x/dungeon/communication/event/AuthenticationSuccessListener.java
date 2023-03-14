package com.momo2x.dungeon.communication.event;

import com.momo2x.dungeon.DungeonException;
import com.momo2x.dungeon.communication.controller.DungeonUpdater;
import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.map.DungeonMap;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static com.momo2x.dungeon.engine.movement.DirectionType.E;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final DungeonMap map;

    private final DungeonUpdater updater;

    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent event) {
        final var userDetails = (UserDetails) event.getAuthentication().getPrincipal();
        final var user = userDetails.getUsername();

        try {
            final var walker = new DungeonWalker(user, "%s-img".formatted(user), true, E);


            this.map.placeElement(
                    walker,
                    user.equals("he") ? new DungeonCoord(1, 3) : new DungeonCoord(1, 1));

            updater.broadcast(walker);

        } catch (DungeonException e) {
            throw new RuntimeException(e);
        }
    }
}
