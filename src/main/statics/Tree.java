package main.statics;

import main.EnumObjects;

public class Tree extends Nature {
    public Tree() {
        super(EnumObjects.TREE);
    }

    @Override
    boolean isGrass() {
        return false;
    }
}
