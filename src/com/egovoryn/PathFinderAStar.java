package com.egovoryn;

import java.util.*;

public class PathFinderAStar {
    private static final int PATH_COST_DIAGONAL = 14;
    private static final int PATH_COST_DIRECT = 10;
    private final Class<? extends Entity> victim;

    public PathFinderAStar(Class<? extends Entity> victim) {
        this.victim = victim;
    }

    public ListForPathfinder<PathNode> getPathToGoal(PathNode start, PathNode goal, WorldMap map) {
        PriorityQueue<ListForPathfinder<PathNode>> openPathNodes = new PriorityQueue<>();
        Map<PathNode, Integer> costFromStart = new HashMap<>();
        costFromStart.put(start, 0);

        ListForPathfinder<PathNode> tmp = new ListForPathfinder<>();
        tmp.add(start);
        openPathNodes.add(tmp);

        while (!openPathNodes.isEmpty()) {
            ListForPathfinder<PathNode> currentPath = openPathNodes.poll();
            if (currentPath == null || currentPath.isEmpty()) return null;
            PathNode currentNode = currentPath.get(currentPath.size() - 1);
            if (currentNode.equals(goal)) return currentPath;
            for (Cell nextCell : findNeighbors(currentNode)) {
                PathNode nextNode = new PathNode(nextCell);
                int newCost = costFromStart.get(currentNode) + costMovingToNeighborPathNode(nextNode, currentNode);
                if ((map.isEmptyCell(nextCell) || map.getTypeCell(nextCell) == victim) && map.isInsideMapBorder(nextCell)) {
                    if (!costFromStart.containsKey(nextNode) || newCost < costFromStart.get(nextNode)) {
                        costFromStart.put(nextNode, newCost);
                        nextNode.setPathCost(newCost + heuristic(nextNode, goal));

                        ListForPathfinder<PathNode> newPath = new ListForPathfinder<>(currentPath);
                        newPath.add(nextNode);
                        openPathNodes.add(newPath);
                    }
                }
            }
        }
        return null;
    }

    public static List<Cell> findNeighbors(Cell current) {
        List<Cell> neighbors = new ArrayList<>();
        int i = current.getY();
        int j = current.getX();
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
        return ((Math.abs(goal.getX() - current.getX())) + Math.abs(goal.getY() - current.getY())) * PATH_COST_DIRECT;
    }

    private int costMovingToNeighborPathNode(PathNode next, PathNode current) {
        return next.getY() != current.getY() && next.getX() != current.getX() ? PATH_COST_DIAGONAL : PATH_COST_DIRECT;
    }

    public PathNode smellVictim(WorldMap map, Cell start) {
        List<Cell> listVictimCells = new ArrayList<>(map.getEntitiesOfType(victim).keySet());
        if (listVictimCells.isEmpty()) return null;
        Cell cellWithMinCost = listVictimCells.get(0);
        int minCost = heuristic(start, cellWithMinCost);
        for (Cell currentCell : listVictimCells) {
            int currentCost = heuristic(start, currentCell);
            if (currentCost < minCost) {
                minCost = currentCost;
                cellWithMinCost = currentCell;
            }
        }
        return new PathNode(cellWithMinCost.getY(), cellWithMinCost.getX());
    }
}
