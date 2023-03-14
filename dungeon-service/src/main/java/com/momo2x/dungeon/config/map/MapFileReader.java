package com.momo2x.dungeon.config.map;

import com.momo2x.dungeon.config.DungeonProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MapFileReader {

    private final DungeonProperties properties;

    public List<String> readMapCoords() throws IOException {
        final var mapFile = this.properties.getMapFile();

        log.info("Reading '{}' with map coordinates and its elements placement", mapFile);

        try (final var input = this.getClass().getResourceAsStream(mapFile)) {
            if (input == null) {
                throw new IOException("'%s' not found".formatted(mapFile));
            }

            try (final var reader = new BufferedReader(new InputStreamReader(input))) {
                final var map = new LinkedList<String>();

                String line;
                while ((line = reader.readLine()) != null) {
                    map.add(line.stripTrailing());
                }

                return map;
            }
        }
    }

    public String readMapElements() throws IOException {
        final var dataFile = this.properties.getDataFile();

        log.info("Reading '{}' with elements information", dataFile);

        try (final var input = this.getClass().getResourceAsStream(dataFile)) {
            if (input == null) {
                throw new IOException("'%s' not found".formatted(dataFile));
            }

            try (final var reader = new BufferedReader(new InputStreamReader(input))) {
                final var builder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line.trim());
                }

                return builder.toString();
            }
        }
    }

}
