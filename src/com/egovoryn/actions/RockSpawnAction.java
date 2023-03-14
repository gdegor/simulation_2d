package com.egovoryn.actions;

import com.egovoryn.Cell;
import com.egovoryn.WorldMap;
import com.egovoryn.statics.Rock;

public class RockSpawnAction extends SpawnAction<Rock> {

    @Override
    protected Rock spawnEntity(Cell c) {
        return new Rock();
    }

    public RockSpawnAction(WorldMap map) {
        float chanceSpawn = super.chancesSpawn[4];
        super.countTypeOnMap = (int) (map.getSizeMap() * chanceSpawn);
    }
}
