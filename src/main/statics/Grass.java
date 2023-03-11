package main.statics;

import main.EnumObjects;

public class Grass extends Nature {

    public Grass() {
        super(EnumObjects.GRASS);
    }

    @Override
    boolean isGrass() {
        return true;
    }
}
