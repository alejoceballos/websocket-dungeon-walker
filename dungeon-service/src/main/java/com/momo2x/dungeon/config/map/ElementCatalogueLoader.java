package com.momo2x.dungeon.config.map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class ElementCatalogueLoader {

    private final String jsonData;

    public Map<String, CatalogueItem> load() throws MalformedCatalogueException {
        log.info("Loading elements in catalogue");

        try {
            return new ObjectMapper()
                    .readValue(
                            this.jsonData,
                            new TypeReference<>() {
                            });
        } catch (JsonProcessingException e) {
            throw new MalformedCatalogueException(e);
        }
    }

}
