package com.momo2x.dungeon.config.map;

import com.momo2x.dungeon.DungeonException;

public class MalformedMapException extends DungeonException {
    public MalformedMapException(String message) {
        super(message);
    }
}
