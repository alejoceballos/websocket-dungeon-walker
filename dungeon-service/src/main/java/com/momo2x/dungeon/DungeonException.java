package com.momo2x.dungeon;

public class DungeonException extends Exception {

    public DungeonException(final String message) {
        super(message);
    }

    public DungeonException(final Throwable cause) {
        super(cause);
    }

    public DungeonException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
