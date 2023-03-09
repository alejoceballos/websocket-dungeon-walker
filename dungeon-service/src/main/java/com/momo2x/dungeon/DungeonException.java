package com.momo2x.dungeon;

public class DungeonException extends Exception {
    public DungeonException(String message) {
        super(message);
    }

    public DungeonException(Throwable cause) {
        super(cause);
    }

    public DungeonException(String message, Throwable cause) {
        super(message, cause);
    }

}
