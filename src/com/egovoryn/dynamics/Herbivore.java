package com.egovoryn.dynamics;

import com.egovoryn.Cell;
import com.egovoryn.PathFinderAStar;
import com.egovoryn.WorldMap;
import com.egovoryn.statics.Grass;

public class Herbivore extends Creature {
    public boolean canAttack = false;

    @Override
    public void makeMove(Cell start, WorldMap map) {
        if (healthPoints >= 8) {
            canAttack = true;
        }
        for (Cell neighbor : PathFinderAStar.findNeighbors(start)) {
            if (map.getTypeCell(neighbor) == Grass.class) {
                Grass grass = (Grass) map.getEntityFromCell(neighbor);
                healthPoints += grass.getHealPower();
                satiety++;
                map.clearCell(neighbor);
                return;
            }
            if (map.getTypeCell(neighbor) == Predator.class && canAttack) {
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
        satiety--;
        super.makeMove(start, map);
    }
}
