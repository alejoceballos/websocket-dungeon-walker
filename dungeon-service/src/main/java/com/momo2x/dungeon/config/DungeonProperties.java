package com.momo2x.dungeon.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "dungeon")
public class DungeonProperties {

    @Value("${dungeon.map.width}")
    @Getter
    private int width;

    @Value("${dungeon.map.height}")
    @Getter
    private int height;

    @Getter
    @Setter
    private List<ElementProperties> elements;

}
