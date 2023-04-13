package com.momo2x.dungeon.config.map;

import com.momo2x.dungeon.config.DungeonProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
public class MapFileReader {

    private final DungeonProperties properties;

    private final Function<File, LinkedList<String>> fileToStringList = file -> {
        log.info("Reading '{}' with map coordinates and its elements placement", file.getName());

        try (final var reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            final var mapAsStringList = new LinkedList<String>();

            String line;
            while (Objects.nonNull(line = reader.readLine())) {
                mapAsStringList.add(line.stripTrailing());
            }

            return mapAsStringList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    public List<LinkedList<String>> readMaps() throws DataFileReaderException {
        try {
            return this
                    .getMapFilesAsStream(this.properties.getMapFilesPrefix())
                    .map(fileToStringList)
                    .toList();

        } catch (final URISyntaxException e) {
            throw new DataFileReaderException("Error reading map files", e);
        }
    }

    public String readElementsCatalogue() throws DataFileReaderException {
        final var dataFile = this.properties.getDataFilePrefix() + ".json";

        log.info("Reading '{}' with elements information", dataFile);

        try (final var input = this.getClass().getResourceAsStream(dataFile)) {
            if (Objects.isNull(input)) {
                throw new IOException("'%s' not found?".formatted(dataFile));
            }

            try (final var reader = new BufferedReader(new InputStreamReader(input))) {
                final var builder = new StringBuilder();

                String line;
                while (Objects.nonNull(line = reader.readLine())) {
                    builder.append(line.trim());
                }

                return builder.toString();
            } catch (IOException e) {
                throw new DataFileReaderException("Unable to read from resource '%s'".formatted(dataFile), e);
            }
        } catch (IOException e) {
            throw new DataFileReaderException("Unable to get resource' %s'".formatted(dataFile), e);
        }
    }

    private Stream<File> getMapFilesAsStream(final String mapFilePathAndPrefix)
            throws URISyntaxException, DataFileReaderException {
        log.info("Checking for maps like '{}[??].map'", mapFilePathAndPrefix);

        final var pathName = mapFilePathAndPrefix.substring(0, mapFilePathAndPrefix.lastIndexOf("/"));
        final var fileNamePrefix = mapFilePathAndPrefix.substring(mapFilePathAndPrefix.lastIndexOf("/") + 1);
        final var filePattern = "%s\\[[0-9][0-9]\\].map".formatted(fileNamePrefix);
        final var resourcePath = this.getClass().getResource(pathName);

        if (Objects.isNull(resourcePath)) {
            throw new DataFileReaderException("No map folder '%s' was found".formatted(pathName));
        }

        final var filteredFiles =
                new File(resourcePath.toURI()).listFiles(file -> Pattern.matches(filePattern, file.getName()));

        return Arrays.stream(Objects.requireNonNull(filteredFiles)).sorted(File::compareTo);
    }

}
