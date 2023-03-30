package com.momo2x.dungeon.engine.actors;

import com.momo2x.dungeon.DungeonException;

public class ElementException extends DungeonException {
    public ElementException(final String message) {
        super(message);
    }

    public ElementException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
