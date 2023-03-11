package main.statics;

import main.EnumObjects;

public class Rock extends Nature {

    public Rock() {
        super(EnumObjects.ROCK);
    }

    @Override
    boolean isGrass() {
        return false;
    }
}
