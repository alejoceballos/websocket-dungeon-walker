package com.momo2x.dungeon.engine.map;

import com.momo2x.dungeon.DungeonException;

public class CoordinateException extends DungeonException {
    public CoordinateException(final String message) {
        super(message);
    }

    public CoordinateException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
