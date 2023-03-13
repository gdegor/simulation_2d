package main.dynamics;

import main.TypeEntity;

public class Herbivore extends Creature {
    private static int hp = 4;

    public Herbivore() {
        super(TypeEntity.HERBIVORE, TypeEntity.GRASS, 1, hp);
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        Herbivore.hp = hp;
    }
}
