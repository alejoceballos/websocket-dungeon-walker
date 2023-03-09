package com.momo2x.dungeon.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dungeon")
public class DungeonProperties {

    @Value("${dungeon.map.map-file}")
    @Getter
    private String mapFile;

    @Value("${dungeon.map.data-file}")
    @Getter
    private String dataFile;

}
