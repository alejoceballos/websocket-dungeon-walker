package com.momo2x.dungeon.engine.actors;

import com.momo2x.dungeon.DungeonException;

public class ElementException extends DungeonException {
    public ElementException(String message) {
        super(message);
    }

    public ElementException(String message, Throwable cause) {
        super(message, cause);
    }
}
