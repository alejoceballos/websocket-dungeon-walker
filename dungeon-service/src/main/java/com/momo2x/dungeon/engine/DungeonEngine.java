package com.momo2x.dungeon.engine;

import com.momo2x.dungeon.communication.controller.DungeonUpdater;
import com.momo2x.dungeon.config.DungeonProperties;
import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import com.momo2x.dungeon.engine.movement.DungeonDirectionType;
import com.momo2x.dungeon.engine.map.DungeonMap;
import com.momo2x.dungeon.engine.movement.MovementManager;
import com.momo2x.dungeon.engine.movement.SimpleBounceStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class DungeonEngine {

    private final DungeonProperties properties;

    private final DungeonMap map;

    private final DungeonUpdater updater;

    private List<MovementManager> managers;

    public void init() {
        this.map.init();
        log.info("Map created (w: {}, h: {})", this.map.getWidth(), this.map.getHeight());

        managers = this.properties.getElements().stream().map(elem -> {
            final var walker = new DungeonWalker(
                    elem.id(),
                    true,
                    DungeonDirectionType.valueOf(elem.direction()));

            log.info("Walker {} created", walker.getId());

            final var bounce = new SimpleBounceStrategy(this.map, walker);

            log.info("Bounce strategy for {} is {}", walker.getId(), bounce.getClass().getSimpleName());

            final var manager = new MovementManager(this.map, walker, bounce);

            log.info("Walker manager for {} created", walker.getId());

            manager.enterMap(new DungeonCoord(elem.x(), elem.y()));

            log.info(
                    "Walker {} entered map at {} and it's ready to go {}",
                    walker.getId(),
                    walker.getCell().getCoord(),
                    walker.getDirection());

            return manager;
        }).collect(Collectors.toList());
    }

    public void run() throws InterruptedException {
        Thread.sleep(5000);

        this.managers.forEach(manager -> Executors.newFixedThreadPool(this.managers.size()).submit(() -> {
            try {
                log.info("Walker {} started moving", manager.getWalker().getId());

                while (true) {
                    Thread.sleep(500);
                    manager.move();
                    this.updater.update(manager.getWalker());
                }
            } catch (Exception e) {
                e.printStackTrace(System.out);
                log.error(
                        "Walker {} at {}",
                        manager.getWalker().getId(),
                        manager.getWalker().getCoord());
            }
        }));
    }

}
