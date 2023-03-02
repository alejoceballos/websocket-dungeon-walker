package com.momo2x.dungeon.engine;

import com.momo2x.dungeon.comm.controller.DungeonUpdater;
import com.momo2x.dungeon.config.DungeonProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class EngineInitializer implements ApplicationRunner {

    private final DungeonUpdater sender;

    private final DungeonProperties dungeon;

    private final DungeonEngine engine;

    @Override
    public void run(ApplicationArguments args) throws InterruptedException {
        engine.init();
        engine.run();
    }

}
