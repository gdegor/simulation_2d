package com.egovoryn.dynamics;

import com.egovoryn.Cell;
import com.egovoryn.PathFinderAStar;
import com.egovoryn.WorldMap;
import com.egovoryn.statics.Grass;

public class Herbivore extends Creature {
    private static final int SATIETY_LIMIT_FOR_ATTACK = 8;
    private static final int DAMAGE_DEALT = 1;
    public boolean canAttack = false;

    @Override
    public void makeMove(Cell start, WorldMap map) {
        if (healthPoints >= SATIETY_LIMIT_FOR_ATTACK) canAttack = true;

        for (Cell neighbor : PathFinderAStar.findNeighbors(start)) {
            if (map.getTypeCell(neighbor) == Grass.class) {
                Grass grass = map.getEntityFromCell(neighbor);
                healthPoints += grass.getHealPower();
                satiety++;
                map.clearCell(neighbor);
                return;
            }
            if (map.getTypeCell(neighbor) == Predator.class && canAttack) {
                Predator predator = map.getEntityFromCell(neighbor);
                if (predator.healthPoints <= 0) {
                    map.clearCell(neighbor);
                } else {
                    predator.healthPoints -= DAMAGE_DEALT;
                }
                return;
            }
        }
        satiety--;
        super.makeMove(start, map);
    }
}
