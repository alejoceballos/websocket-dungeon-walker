package com.momo2x.dungeon.config.map;

import com.momo2x.dungeon.DungeonException;

public class DataFileReaderException extends DungeonException {

    public DataFileReaderException(final String message) {
        super(message);
    }

    public DataFileReaderException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
