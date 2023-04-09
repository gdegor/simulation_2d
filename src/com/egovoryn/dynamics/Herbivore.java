package com.egovoryn.dynamics;

import com.egovoryn.Cell;
import com.egovoryn.WorldMap;
import com.egovoryn.statics.Grass;

public class Herbivore extends Creature {
    public boolean danger = false;

    @Override
    public void makeMove(Cell start, WorldMap map) {
        if (healthPoints >= 8) {
            danger = true;
        }
        for (Cell neighbor : findNeighbors(start)) {
            if (map.getTypeCell(neighbor) == Grass.class) {
                Grass grass = (Grass) map.getEntityFromCell(neighbor);
                healthPoints += grass.getHealPower();
                bellyful++;
                map.clearCell(neighbor);
                return;
            }
            if (map.getTypeCell(neighbor) == Predator.class && danger) {
                Predator predator = (Predator) map.getEntityFromCell(neighbor);
                if (predator.healthPoints <= 0) {
                    map.clearCell(neighbor);
                } else {
                    int damageDealt = 1;
                    predator.healthPoints -= damageDealt;
                }
                return;
            }
        }
        bellyful--;
        super.makeMove(start, map);
    }
}
