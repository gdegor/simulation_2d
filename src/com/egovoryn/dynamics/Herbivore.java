package com.egovoryn.dynamics;

import com.egovoryn.Cell;
import com.egovoryn.TypeEntity;
import com.egovoryn.WorldMap;
import com.egovoryn.statics.Grass;

public class Herbivore extends Creature {
    public Herbivore() {
        super(TypeEntity.HERBIVORE);
    }

    @Override
    public void makeMove(Cell start, WorldMap map) {
        for (Cell neighbor : findNeighbors(start)) {
            if (map.getTypeCell(neighbor) == TypeEntity.GRASS) {
                Grass grass = (Grass) map.getEntityFromCell(neighbor);
                healthPoints += grass.getHealPower();
                hungryIndicator++;
                map.clearCell(neighbor);
                return;
            }
        }
        hungryIndicator--;
        super.makeMove(start, map);
    }
}
