package com.momo2x.dungeon.engine;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EngineInitializer implements ApplicationRunner {

    private final DungeonEngine engine;

    @Override
    public void run(ApplicationArguments args) throws InterruptedException {
        this.engine.init();
        this.engine.run();
    }

}
