package com.egovoryn;

import java.util.Objects;

public class Cell implements Comparable<Cell> {
    private final int x;
    private final int y;
    private int pathCost = 0;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public Cell(int y, int x) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                ", pathCost=" + pathCost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(Cell o) {
        return this.pathCost - o.pathCost;
    }
}
