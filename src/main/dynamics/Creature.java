package main.dynamics;

import main.Entity;

public abstract class Creature extends Entity {
    int speedMove;
    int healthPoints;
    public abstract void makeMove();
}
