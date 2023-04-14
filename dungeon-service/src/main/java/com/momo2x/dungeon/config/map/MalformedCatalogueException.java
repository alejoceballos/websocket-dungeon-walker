package com.momo2x.dungeon.config.map;

import com.momo2x.dungeon.DungeonException;

public class MalformedCatalogueException extends DungeonException {

    public MalformedCatalogueException(String message) {
        super(message);
    }

    public MalformedCatalogueException(final Throwable throwable) {
        super(throwable);
    }

}
