package com.egovoryn.actions;

import com.egovoryn.Cell;
import com.egovoryn.Entity;
import com.egovoryn.WorldMap;

public abstract class SpawnAction<T extends Entity> extends Action {
    protected int countTypeOnMap;
    protected final float[] chancesSpawn = {0.03f, 0.07f, 0.10f, 0.18f, 0.10f}; // predators, herbivore, tree, grass, rock

    public void perform(WorldMap map) {
        int currentRate = 0;
        while (currentRate < countTypeOnMap) {
            Cell cell = getEmptyRandomCoordinates(map);
            map.setEntityInCell(cell, spawnEntity(cell));
            currentRate++;
        }
    }

    public Cell getEmptyRandomCoordinates(WorldMap map) {
        while (true) {
            int x = (int) (Math.random() * map.getX());
            int y = (int) (Math.random() * map.getY());
            Cell cell = new Cell(y, x);
            if (map.isEmptyCell(cell) && map.isInsideMapBorder(cell)) {
                return cell;
            }
        }
    }

    protected abstract T spawnEntity(Cell c);
}
