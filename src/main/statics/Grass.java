package main.statics;

import main.TypeEntity;

public class Grass extends Nature {

    public Grass() {
        super(TypeEntity.GRASS);
    }

    @Override
    boolean isGrass() {
        return true;
    }
}
