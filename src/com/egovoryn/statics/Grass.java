package com.egovoryn.statics;

import com.egovoryn.TypeEntity;

public class Grass extends Nature {
    private int healPower = 1;

    public int getHealPower() {
        return healPower;
    }

    public Grass() {
        super(TypeEntity.GRASS);
    }
}
