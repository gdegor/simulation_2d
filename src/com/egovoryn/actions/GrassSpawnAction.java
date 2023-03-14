package com.egovoryn.actions;

import com.egovoryn.Cell;
import com.egovoryn.WorldMap;
import com.egovoryn.statics.Grass;

public class GrassSpawnAction extends SpawnAction<Grass> {
    @Override
    protected Grass spawnEntity(Cell c) {
        return new Grass();
    }

    public GrassSpawnAction(WorldMap map) {
        float chanceSpawn = super.chancesSpawn[3];
        super.countTypeOnMap = (int) (map.getSizeMap() * chanceSpawn);
    }
}
