package com.egovoryn.dynamics;

import com.egovoryn.Cell;
import com.egovoryn.TypeEntity;
import com.egovoryn.WorldMap;
import com.egovoryn.statics.Grass;

public class Herbivore extends Creature {
    public Herbivore() {
        super(TypeEntity.HERBIVORE, TypeEntity.GRASS, 1, 5);
    }

    @Override
    public void makeMove(Cell start, WorldMap map) {
        for (Cell neighbor : findNeighbors(start)) {
            if (map.getTypeCell(neighbor) == TypeEntity.GRASS) {
                Grass grass = (Grass) map.getEntityFromCell(neighbor);
                setHealthPoints(getHealthPoints() + grass.getHealPower());
                map.clearCell(neighbor);
                return;
            }
        }
        super.makeMove(start, map);
    }
}
