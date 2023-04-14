package com.egovoryn;

import java.util.*;

public class PathFinderAStar {
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
            ListForPathfinder<PathNode> path = openPathNodes.poll();
            if (path == null || path.isEmpty()) return null;
            PathNode node = path.get(path.size() - 1);
            if (node.equals(goal)) return path;
            for (Cell nextCell : findNeighbors(node)) {
                PathNode nextPathNode = new PathNode(nextCell);
                int newCost = costFromStart.get(node) + costMovingToNeighborPathNode(nextPathNode, node);
                if ((map.isEmptyCell(nextCell) || map.getTypeCell(nextCell) == victim) && map.isInsideMapBorder(nextCell)) {
                    if (!costFromStart.containsKey(nextPathNode) || newCost < costFromStart.get(nextPathNode)) {
                        costFromStart.put(nextPathNode, newCost);
                        nextPathNode.setPathCost(newCost + heuristic(nextPathNode, goal));

                        ListForPathfinder<PathNode> newPath = new ListForPathfinder<>(path);
                        newPath.add(nextPathNode);
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
        return ((Math.abs(goal.getX() - current.getX())) + Math.abs(goal.getY() - current.getY())) * 10;
    }

    private int costMovingToNeighborPathNode(PathNode next, PathNode current) {
        return next.getY() != current.getY() && next.getX() != current.getX() ? 14 : 10;
    }

    public PathNode smellVictim(WorldMap map, Cell start) {
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
            return new PathNode(res.getY(), res.getX());
        }
        return null;
    }
}
