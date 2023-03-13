package main.dynamics;

import main.TypeEntity;

public class Predator extends Creature {
    int damageDealt = 2;

    public int getDamageDealt() {
        return damageDealt;
    }

    public Predator() {
        super(TypeEntity.PREDATOR, TypeEntity.HERBIVORE, 2, 3);
    }
}
