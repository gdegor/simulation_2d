package com.egovoryn;

import java.util.HashMap;
import java.util.Map;

public class WorldMap {
    private final int Y = 10; // height
    private final int X = 10; // width
    private final Map<Cell, Entity> cells = new HashMap<>();

    public void setEntityInCell(Cell cell, Entity entity) {
        cells.put(cell, entity);
    }

    public Class<? extends Entity> getTypeCell(Cell cell) {
        return cells.get(cell) != null ? cells.get(cell).getClass() : null;
    }

    public <T extends Entity> T getEntityFromCell(Cell cell) {
        return (T) cells.get(cell);
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
        return !cells.containsKey(cell);
    }

    public boolean isInsideMapBorder(Cell cell) {
        return cell.getY() < this.Y && cell.getX() < this.X;
    }

    public void makeMove(Cell start, Cell move) {
        setEntityInCell(move, getEntityFromCell(start));
        clearCell(start);
    }

    public void clearCell(Cell cell) {
        cells.remove(cell);
    }

    public <T> HashMap<Cell, T> getEntitiesOfType(Class<T> typeEntity) {
        HashMap<Cell, T> result = new HashMap<>();
        for (Map.Entry<Cell, Entity> entry : cells.entrySet()) {
            if (typeEntity.isInstance(entry.getValue())) {
                //noinspection unchecked
                result.put(entry.getKey(), (T) entry.getValue());
            }
        }
        return result;
    }
}
