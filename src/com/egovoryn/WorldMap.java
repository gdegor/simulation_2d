package com.egovoryn;

import com.egovoryn.dynamics.Creature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WorldMap {
    private final int Y = 10; // height
    private final int X = 10; // width

    public int numberIteration = 1;

    private final Map<Cell, Entity> cells = new HashMap<>();

    public void add(Cell cell, Entity entity) {
        cells.put(cell, entity);
    }

    public TypeEntity getTypeCell(Cell cell) {
        return cells.containsKey(cell) ? cells.get(cell).getType() : TypeEntity.NONE;
    }

    public Entity getEntityFromCell(Cell cell) {
        return cells.get(cell);
    }

    public int getSizeMap() {
        return Y * X;
    }

    public int getY() {
        return Y;
    }

    public int getX() {
        return X;
    }

    public boolean isEmptyCell(Cell cell) {
        return !cells.containsKey(cell) && cell.getY() < this.Y && cell.getX() < this.X;
    }

    public void setEntityInCell(Cell cell, Creature creature) {
        cells.remove(cell);
        cells.put(cell, creature);
    }

    public void clearCell(Cell cell) {
        cells.remove(cell);
    }

    public void clearMap() {
        cells.clear();
    }

    public ArrayList<Cell> getAllByType(TypeEntity typeEntity) {
        ArrayList<Cell> result = new ArrayList<>();
        for (Map.Entry<Cell, Entity> entry : cells.entrySet()) {
            Entity tmp = entry.getValue();
            if (tmp.getType() == typeEntity) {
                result.add(entry.getKey());
            }
        }
        return result;
    }
}
