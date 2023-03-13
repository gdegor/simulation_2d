package main.statics;

import main.TypeEntity;

public class Rock extends Nature {

    public Rock() {
        super(TypeEntity.ROCK);
    }

    @Override
    boolean isGrass() {
        return false;
    }
}
