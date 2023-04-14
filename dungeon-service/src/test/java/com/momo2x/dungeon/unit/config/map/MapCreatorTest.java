package com.momo2x.dungeon.unit.config.map;

import com.momo2x.dungeon.config.map.MalformedCatalogueException;
import com.momo2x.dungeon.config.map.MalformedMapException;
import com.momo2x.dungeon.config.map.MapCreator;
import com.momo2x.dungeon.engine.map.DungeonCoord;
import org.junit.jupiter.api.Test;

import static com.momo2x.dungeon.unit.MapTestUtil.mockMapCells;
import static com.momo2x.dungeon.unit.MapTestUtil.mockMapMetadata;
import static com.momo2x.dungeon.unit.MapTestUtil.mockMapWalkers;
import static com.momo2x.dungeon.unit.MapTestUtil.mockUploadedCatalogue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

class MapCreatorTest {

    @Test
    void create() throws MalformedMapException, MalformedCatalogueException {
        final var dungeonMap = new MapCreator(mockMapMetadata.get(), mockUploadedCatalogue.get()).create();

        assertThat(dungeonMap.getWidth(), equalTo(6));
        assertThat(dungeonMap.getHeight(), equalTo(5));
        assertThat(dungeonMap.getNumOfLayers(), equalTo(3));

        assertThat(dungeonMap.getWalkers().size(), equalTo(1));
        assertThat(
                dungeonMap.getWalkers().values(),
                containsInAnyOrder(mockMapWalkers.get().values().toArray()));

        assertThat(dungeonMap.getMap().size(), equalTo(30));
        assertThat(
                dungeonMap.getMap().entrySet(),
                containsInAnyOrder(mockMapCells.get().entrySet().toArray()));

        final var walkerOnGrassCell = dungeonMap.getMap().get(new DungeonCoord(2, 2));

        assertThat(walkerOnGrassCell.getTopElement().getId(), equalTo("001"));
        walkerOnGrassCell.removeTopElement();
        assertThat(walkerOnGrassCell.getTopElement().getId(), equalTo("G01"));
    }

}