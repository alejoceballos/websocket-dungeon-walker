package com.momo2x.dungeon.engine.map;

import com.momo2x.dungeon.DungeonException;

public class CoordinateException extends DungeonException {
    public CoordinateException(String message) {
        super(message);
    }

    public CoordinateException(String message, Throwable cause) {
        super(message, cause);
    }
}
