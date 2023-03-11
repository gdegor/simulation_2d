package main.statics;

import main.Entity;
import main.EnumObjects;

public abstract class Nature extends Entity {
    boolean edible;

    public Nature(EnumObjects type) {
        super(type);
    }

    abstract boolean isGrass();
}
