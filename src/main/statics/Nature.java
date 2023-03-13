package main.statics;

import main.Entity;
import main.TypeEntity;

public abstract class Nature extends Entity {
    boolean edible;

    public Nature(TypeEntity type) {
        super(type);
    }

    abstract boolean isGrass();
}
