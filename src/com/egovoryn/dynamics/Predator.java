package com.egovoryn.dynamics;

import com.egovoryn.Cell;
import com.egovoryn.WorldMap;

public class Predator extends Creature {
    int damageDealt = 2;
    @Override
    public void makeMove(Cell start, WorldMap map) {
        for (Cell neighbor : findNeighbors(start)) {
            if (map.getTypeCell(neighbor) == Herbivore.class) {
                Herbivore herbivore = (Herbivore) map.getEntityFromCell(neighbor);
                if (herbivore.healthPoints <= 0) {
                    map.clearCell(neighbor);
                } else {
                    herbivore.healthPoints -= damageDealt;
                }
                bellyful++;
                return;
            }
        }
        bellyful--;
        super.makeMove(start, map);
    }
}
