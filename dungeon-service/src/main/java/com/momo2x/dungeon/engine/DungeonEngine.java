package com.momo2x.dungeon.engine;

import com.momo2x.dungeon.communication.controller.out.DungeonUpdater;
import com.momo2x.dungeon.engine.actors.DungeonAutonomousWalker;
import com.momo2x.dungeon.engine.interaction.bounce.BounceException;
import com.momo2x.dungeon.engine.interaction.bounce.BounceStrategy;
import com.momo2x.dungeon.engine.interaction.bounce.SimpleBounceStrategy;
import com.momo2x.dungeon.engine.map.DungeonMap;
import com.momo2x.dungeon.engine.movement.MovementManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import static com.momo2x.dungeon.engine.interaction.bounce.BounceStrategyType.SIMPLE;

@Slf4j
@Component
@RequiredArgsConstructor
public class DungeonEngine {

    private final DungeonMap map;

    private final DungeonUpdater updater;

    @Getter
    private final List<MovementManager> managers = new ArrayList<>();

    public void init() {
        this.managers.addAll(this.map
                .getWalkers()
                .values()
                .stream()
                .filter(DungeonAutonomousWalker.class::isInstance)
                .map(walker -> {
                    log.info(
                            "Processing walker {} at {} going to {}",
                            walker.getId(),
                            walker.getCoord(),
                            walker.getDirection());

                    final BounceStrategy bounce;

                    try {
                        bounce = createStrategy(this.map, (DungeonAutonomousWalker) walker);
                    } catch (BounceException e) {
                        throw new RuntimeException(e);
                    }

                    log.info("Bounce strategy for {} is {}", walker.getId(), bounce.getClass().getSimpleName());

                    final var manager = new MovementManager(this.map, (DungeonAutonomousWalker) walker, bounce);

                    log.info("Walker manager for {} created", walker.getId());

                    return manager;
                }).toList());
    }

    public void run() throws InterruptedException {
        Thread.sleep(5000);

        this.managers.forEach(manager -> Executors.newFixedThreadPool(this.managers.size()).submit(() -> {
            try {
                log.info("Walker {} started moving", manager.getWalker().getId());

                while (true) {
                    Thread.sleep(manager.calculateSleepTme());
                    manager.move();
                    this.updater.broadcast(manager.getWalker());
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

    private BounceStrategy createStrategy(
            final DungeonMap map,
            final DungeonAutonomousWalker walker
    ) throws BounceException {
        if (walker.getBounceStrategy() == SIMPLE) {
            return new SimpleBounceStrategy(map, walker);
        }

        throw new BounceException("Unmapped bounce strategy");
    }

}
