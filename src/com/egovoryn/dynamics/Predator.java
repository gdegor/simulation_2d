package com.egovoryn.dynamics;

import com.egovoryn.Cell;
import com.egovoryn.TypeEntity;
import com.egovoryn.WorldMap;

public class Predator extends Creature {
    int damageDealt = 2;

    public Predator() {
        super(TypeEntity.PREDATOR);
    }

    @Override
    public void makeMove(Cell start, WorldMap map) {
        for (Cell neighbor : findNeighbors(start)) {
            if (map.getTypeCell(neighbor) == TypeEntity.HERBIVORE) {
                Herbivore herbivore = (Herbivore) map.getEntityFromCell(neighbor);
                herbivore.healthPoints -= damageDealt;
                hungryIndicator++;
                return;
            }
        }
        hungryIndicator--;
        super.makeMove(start, map);
    }
}
