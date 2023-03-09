package com.momo2x.dungeon.unit.config.map;

import com.momo2x.dungeon.config.map.MalformedMapException;
import com.momo2x.dungeon.config.map.MapLoader;
import org.junit.jupiter.api.Test;

import static com.momo2x.dungeon.unit.config.map.MapTestUtil.LOADED_MAP;
import static com.momo2x.dungeon.unit.config.map.MapTestUtil.RAW_MAP;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class MapLoaderTest {

    @Test
    void load() throws MalformedMapException {
        assertThat(new MapLoader(RAW_MAP).load(), equalTo(LOADED_MAP));
    }

}