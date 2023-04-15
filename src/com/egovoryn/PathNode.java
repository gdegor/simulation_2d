package com.egovoryn;

public class PathNode extends Cell {
    public PathNode(int y, int x) {
        super(y, x);
    }

    private int pathCost = 0;

    public PathNode(Cell cell) {
        super(cell.getY(), cell.getX());
    }

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    @Override
    public String toString() {
        return "PathNode{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", pathCost=" + pathCost +
                '}';
    }
}
