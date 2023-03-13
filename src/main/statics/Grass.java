package main.statics;

import main.TypeEntity;

public class Grass extends Nature {
    int healPower = 2;

    public int getHealPower() {
        return healPower;
    }

    public Grass() {
        super(TypeEntity.GRASS);
    }

    @Override
    boolean isGrass() {
        return true;
    }
}
