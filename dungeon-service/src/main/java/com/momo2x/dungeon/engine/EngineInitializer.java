package com.momo2x.dungeon.engine;

import com.momo2x.dungeon.config.DungeonProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EngineInitializer implements ApplicationRunner {

    private final DungeonProperties dungeon;

    private final DungeonEngine engine;

    @Override
    public void run(ApplicationArguments args) throws InterruptedException {
        engine.init();
        engine.run();
    }

}
