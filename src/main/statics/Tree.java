package main.statics;

import main.TypeEntity;

public class Tree extends Nature {
    public Tree() {
        super(TypeEntity.TREE);
    }

    @Override
    boolean isGrass() {
        return false;
    }
}
