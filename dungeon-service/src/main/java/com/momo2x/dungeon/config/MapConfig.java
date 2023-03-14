package com.momo2x.dungeon.config;

import com.momo2x.dungeon.config.map.ElementCatalogueLoader;
import com.momo2x.dungeon.config.map.MalformedCatalogueException;
import com.momo2x.dungeon.config.map.MalformedMapException;
import com.momo2x.dungeon.config.map.MapCreator;
import com.momo2x.dungeon.config.map.MapFileReader;
import com.momo2x.dungeon.config.map.MapLoader;
import com.momo2x.dungeon.engine.map.DungeonMap;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Slf4j
@Configuration
@AllArgsConstructor
public class MapConfig {

    private final DungeonProperties properties;

    @Bean
    public DungeonMap dungeonMap() throws IOException, MalformedMapException, MalformedCatalogueException {
        log.info("Configuring map and its elements");

        final var fileReader = new MapFileReader(this.properties);

        final var rawMap = fileReader.readMapCoords();
        final var elementsCoordsMap = new MapLoader(rawMap).load();

        final var rawJsonData = fileReader.readMapElements();
        final var catalogueMap = new ElementCatalogueLoader(rawJsonData).load();

        return new MapCreator(elementsCoordsMap, catalogueMap).create();
    }

}
