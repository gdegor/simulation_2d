package main.dynamics;

import main.Entity;
import main.EnumObjects;

public abstract class Creature extends Entity {
    int speedMove;
    int healthPoints;

    public Creature(EnumObjects type) {
        super(type);
    }

    public abstract void makeMove();
}
