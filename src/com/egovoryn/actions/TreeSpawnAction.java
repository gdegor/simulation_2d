package com.egovoryn.actions;

import com.egovoryn.Cell;
import com.egovoryn.WorldMap;
import com.egovoryn.statics.Tree;

public class TreeSpawnAction extends SpawnAction<Tree> {
    @Override
    protected Tree spawnEntity(Cell c) {
        return new Tree();
    }

    public TreeSpawnAction(WorldMap map) {
        float chanceSpawn = super.chancesSpawn[2];
        super.countTypeOnMap = (int) (map.getSizeMap() * chanceSpawn);
    }
}
