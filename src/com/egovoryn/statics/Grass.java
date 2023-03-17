package com.egovoryn.statics;

import com.egovoryn.TypeEntity;

public class Grass extends Nature {

    public int getHealPower() {
        return 1;
    }

    public Grass() {
        super(TypeEntity.GRASS);
    }
}
