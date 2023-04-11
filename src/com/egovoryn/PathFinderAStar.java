package com.egovoryn;

import java.util.*;

public class PathFinderAStar {
    private final Class<? extends Entity> victim;

    public PathFinderAStar(Class<? extends Entity> victim) {
        this.victim = victim;
    }

    public ListForPathfinder<Cell> getPathToGoal(Cell start, Cell goal, WorldMap map) {
        PriorityQueue<ListForPathfinder<Cell>> openCells = new PriorityQueue<>();
        Map<Cell, Integer> costFromStart = new HashMap<>();
        costFromStart.put(start, 0);

        ListForPathfinder<Cell> tmp = new ListForPathfinder<>();
        tmp.add(start);
        openCells.add(tmp);

        while (!openCells.isEmpty()) {
            ListForPathfinder<Cell> path = openCells.poll();
            if (path == null || path.isEmpty()) return null;
            Cell node = path.get(path.size() - 1);
            if (node.equals(goal)) return path;
            for (Cell nextCell : findNeighbors(node)) {
                int newCost = costFromStart.get(node) + costMovingToNeighborCell(nextCell, node);
                if ((map.isEmptyCell(nextCell) || map.getTypeCell(nextCell) == victim) && !map.isBorderMap(nextCell)) {
                    if (!costFromStart.containsKey(nextCell) || newCost < costFromStart.get(nextCell)) {
                        costFromStart.put(nextCell, newCost);
                        nextCell.setPathCost(newCost + heuristic(nextCell, goal));

                        ListForPathfinder<Cell> newPath = new ListForPathfinder<>(path);
                        newPath.add(nextCell);
                        openCells.add(newPath);
                    }
                }
            }
        }
        return null;
    }

    public static List<Cell> findNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int i = cell.getY();
        int j = cell.getX();
        for (int row = i - 1; row < i + 2; row++) {
            for (int col = j - 1; col < j + 2; col++) {
                if ((row != i || col != j) && (row >= 0) && (col >= 0)) {
                    neighbors.add(new Cell(row, col));
                }
            }
        }
        return neighbors;
    }

    private int heuristic(Cell current, Cell goal) {
        return ((Math.abs(goal.getX() - current.getX())) + Math.abs(goal.getY() - current.getY())) * 10;
    }

    private int costMovingToNeighborCell(Cell next, Cell current) {
        return next.getY() != current.getY() && next.getX() != current.getX() ? 14 : 10;
    }

    public Cell smellVictim(WorldMap map, Cell start) {
        List<Cell> allVictims = new ArrayList<>(map.getEntitiesOfType(victim).keySet());
        if (!allVictims.isEmpty()) {
            Cell res = allVictims.get(0);
            int min = heuristic(start, res);
            for (Cell tmp : allVictims) {
                int tmpHeuristic = heuristic(start, tmp);
                if (tmpHeuristic < min) {
                    min = tmpHeuristic;
                    res = tmp;
                }
            }
            return res;
        }
        return null;
    }
}
