package com.momo2x.dungeon.engine;

import com.momo2x.dungeon.communication.controller.DungeonUpdater;
import com.momo2x.dungeon.engine.actors.DungeonWalker;
import com.momo2x.dungeon.engine.map.DungeonMap;
import com.momo2x.dungeon.engine.movement.BounceException;
import com.momo2x.dungeon.engine.movement.BounceStrategy;
import com.momo2x.dungeon.engine.movement.MovementManager;
import com.momo2x.dungeon.engine.movement.SimpleBounceStrategy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import static com.momo2x.dungeon.engine.movement.BounceStrategyType.SIMPLE;

@Slf4j
@Component
@RequiredArgsConstructor
public class DungeonEngine {

    private final DungeonMap map;

    private final DungeonUpdater updater;

    @Getter
    private final List<MovementManager> managers = new ArrayList<>();

    public void init() {
        managers.addAll(this.map
                .getWalkers().stream().map(walker -> {
                    log.info(
                            "Processing walker {} at {} going to {}",
                            walker.getId(),
                            walker.getCoord(),
                            walker.getDirection());

                    final BounceStrategy bounce;

                    try {
                        bounce = createStrategy(map, walker);
                    } catch (BounceException e) {
                        throw new RuntimeException(e);
                    }

                    log.info("Bounce strategy for {} is {}", walker.getId(), bounce.getClass().getSimpleName());

                    final var manager = new MovementManager(this.map, walker, bounce);

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

    private BounceStrategy createStrategy(final DungeonMap map, final DungeonWalker walker) throws BounceException {
        if (walker.getBounceStrategy() == SIMPLE) {
            return new SimpleBounceStrategy(map, walker);
        }

        throw new BounceException("Unmapped bounce strategy");
    }

}
