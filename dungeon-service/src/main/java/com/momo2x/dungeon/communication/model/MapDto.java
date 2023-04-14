package com.momo2x.dungeon.communication.model;

import lombok.Builder;

import java.util.Set;

@Builder
public record MapDto(
        int width,
        int height,
        int numOfLayers,
        Set<ElementDto> elements
) {
}
