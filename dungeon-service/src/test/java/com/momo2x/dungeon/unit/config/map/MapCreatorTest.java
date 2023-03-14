package com.momo2x.dungeon.unit.config.map;

import com.momo2x.dungeon.config.map.MalformedCatalogueException;
import com.momo2x.dungeon.config.map.MalformedMapException;
import com.momo2x.dungeon.config.map.MapCreator;
import org.junit.jupiter.api.Test;

import static com.momo2x.dungeon.unit.MapTestUtil.mockLoadedMap;
import static com.momo2x.dungeon.unit.MapTestUtil.mockMapCells;
import static com.momo2x.dungeon.unit.MapTestUtil.mockMapWalkers;
import static com.momo2x.dungeon.unit.MapTestUtil.mockMapWalls;
import static com.momo2x.dungeon.unit.MapTestUtil.mockUploadedCatalogue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

class MapCreatorTest {

    @Test
    void create() throws MalformedMapException, MalformedCatalogueException {
        final var dungeonMap = new MapCreator(mockLoadedMap.get(), mockUploadedCatalogue.get()).create();

        assertThat(dungeonMap.getWidth(), equalTo(6));
        assertThat(dungeonMap.getHeight(), equalTo(5));

        assertThat(dungeonMap.getWalls().size(), equalTo(1));
        assertThat(
                dungeonMap.getWalls(),
                containsInAnyOrder(mockMapWalls.get().toArray()));

        assertThat(dungeonMap.getWalkers().size(), equalTo(1));
        assertThat(
                dungeonMap.getWalkers().values(),
                containsInAnyOrder(mockMapWalkers.get().values().toArray()));

        assertThat(dungeonMap.getMap().size(), equalTo(30));
        assertThat(
                dungeonMap.getMap().entrySet(),
                containsInAnyOrder(mockMapCells.get().entrySet().toArray()));
    }


}