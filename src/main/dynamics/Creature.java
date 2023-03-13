package main.dynamics;

import main.Cell;
import main.Entity;
import main.TypeEntity;
import main.WorldMap;

import java.util.*;

public abstract class Creature extends Entity {
    private int speedMove = 2;
    private int healthPoints = 2;
    static TypeEntity victim = null;

    public Creature(TypeEntity type, TypeEntity victim, int speedMove, int healthPoints) {
        super(type);
        Creature.victim = victim;
        this.speedMove = speedMove;
        this.healthPoints = healthPoints;
    }

    protected static Stack<Cell> getPathToGoal(Cell start, Cell goal, WorldMap map) {
        Queue<Cell> queue = new PriorityQueue<>();
        queue.add(start);
        Map<Cell, Integer> costSoFar = new HashMap<>();
        costSoFar.put(start, 0);
        Map<Cell, Cell> cameFrom = new HashMap<>();
        cameFrom.put(start, null);

        while (!queue.isEmpty()) {
            Cell currentCell = queue.poll();

            if (currentCell == goal) break;

            for (Cell nextCell : findNeighbors(currentCell)) {
                int newCost = costSoFar.get(currentCell) + costPath(nextCell, currentCell);
                if (nextCell.getX() < map.getX() && nextCell.getY() < map.getY()
                        && (map.isEmptyCell(nextCell) || map.getTypeCell(nextCell) == victim)) {
                    if (!costSoFar.containsKey(nextCell) || newCost < costSoFar.get(nextCell)) {
                        costSoFar.put(nextCell, newCost);
                        nextCell.setPathCost(newCost + heuristic(nextCell, goal));
                        queue.add(nextCell);
                        cameFrom.put(nextCell, currentCell);
                    }
                }
            }
        }

        Stack<Cell> result = new Stack<>();
        Cell curr = goal;
        while (curr != start) {
            curr = cameFrom.get(curr);
            result.push(curr);
        }
        return result;
    }

    private static List<Cell> findNeighbors(Cell cell) {
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

    private static int heuristic(Cell current, Cell goal) {
        return ((goal.getX() - current.getX()) + (goal.getY() - current.getY()))*10;
    }

    private static int costPath(Cell next, Cell current) {
        return next.getY() != current.getY() && next.getX() != current.getX() ? 14 : 10;
    }

    protected Cell makeMove(Cell start, Cell goal, WorldMap map, Entity entity) {
        Stack<Cell> path = getPathToGoal(start, goal, map);
        map.clearCell(start);
        Cell move = path.pop();
        for (int i = 0; i < speedMove; i++) {
            if (!path.empty()) move = path.pop();
        }
        map.setEntityInCell(move, entity);
        return move;
    }
}
