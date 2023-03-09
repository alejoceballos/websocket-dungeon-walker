package com.momo2x.dungeon.unit.config.map;

import com.momo2x.dungeon.config.map.ElementCatalogueLoader;
import com.momo2x.dungeon.config.map.MalformedCatalogueException;
import org.junit.jupiter.api.Test;

import static com.momo2x.dungeon.unit.config.map.MapTestUtil.LOADED_CATALOGUE;
import static com.momo2x.dungeon.unit.config.map.MapTestUtil.RAW_CATALOGUE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ElementCatalogueLoaderTest {

    @Test
    void load() throws MalformedCatalogueException {
        assertThat(
                new ElementCatalogueLoader(RAW_CATALOGUE).load().entrySet(),
                equalTo(LOADED_CATALOGUE.entrySet()));
    }

}