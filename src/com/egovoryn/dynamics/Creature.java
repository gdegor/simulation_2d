package com.egovoryn.dynamics;

import com.egovoryn.Cell;
import com.egovoryn.Entity;
import com.egovoryn.TypeEntity;
import com.egovoryn.WorldMap;

import java.util.*;

public abstract class Creature extends Entity {
    private final int speedMove;
    private final TypeEntity victim;
    protected int healthPoints;
    protected int bellyful;


    public Creature(TypeEntity type) {
        super(type);
        victim = type == TypeEntity.PREDATOR ? TypeEntity.HERBIVORE : TypeEntity.GRASS;
        speedMove = type == TypeEntity.PREDATOR ? 2 : 1;
        healthPoints = type == TypeEntity.PREDATOR ? 3 : 5;
        bellyful = type == TypeEntity.PREDATOR ? 5 : 4;
    }

    protected Stack<Cell> getPathToGoal(Cell start, Cell goal, WorldMap map) {
        Queue<Cell> openCells = new PriorityQueue<>();
        Map<Cell, Integer> costFromStart = new HashMap<>();
        Map<Cell, Cell> rootCell = new HashMap<>();
        openCells.add(start);
        costFromStart.put(start, 0);
        rootCell.put(start, null);

        while (!openCells.isEmpty()) {
            Cell currentCell = openCells.poll();
            if (currentCell == goal) break;
            for (Cell nextCell : findNeighbors(currentCell)) {
                int newCost = costFromStart.get(currentCell) + costPath(nextCell, currentCell);
                if ((map.isEmptyCell(nextCell) || map.getTypeCell(nextCell) == victim)
                        && checkBorder(nextCell, map)) {
                    if (!costFromStart.containsKey(nextCell) || newCost < costFromStart.get(nextCell)) {
                        costFromStart.put(nextCell, newCost);
                        nextCell.setPathCost(newCost + heuristic(nextCell, goal));
                        openCells.add(nextCell);
                        rootCell.put(nextCell, currentCell);
                    }
                }
            }
        }
        return generateRoute(goal, start, rootCell);
    }

    protected List<Cell> findNeighbors(Cell cell) {
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

    private Stack<Cell> generateRoute(Cell goal, Cell start, Map<Cell, Cell> rootCell) {
        Stack<Cell> result = new Stack<>();
        Cell curr = goal;
        while (curr != start) {
            curr = rootCell.get(curr);
            if (curr == null) return null;
            result.push(curr);
        }
        return result;
    }

    private int heuristic(Cell current, Cell goal) {
        return ((Math.abs(goal.getX() - current.getX())) + Math.abs(goal.getY() - current.getY())) * 10;
    }

    private int costPath(Cell next, Cell current) {
        return next.getY() != current.getY() && next.getX() != current.getX() ? 14 : 10;
    }

    private boolean checkBorder(Cell cell, WorldMap map) {
        return cell.getX() < map.getX() && cell.getY() < map.getY();
    }

    public void makeMove(Cell start, WorldMap map) {
        if (bellyful <= 0) healthPoints--;
        if (healthPoints <= 0) {
            map.clearCell(start);
        } else {
            Cell goal = smellVictim(map, start);
            if (goal != null) {
                Stack<Cell> path = getPathToGoal(start, goal, map);
                if (path != null && !path.empty()) {
                    map.clearCell(start);
                    Cell move = path.pop();
                    int maxSteps = Math.min(speedMove, path.size());
                    for (int i = 0; i < maxSteps; i++) {
                        move = path.pop();
                    }
                    map.setEntityInCell(move, this);
                }
            }
        }
    }

    private Cell smellVictim(WorldMap map, Cell start) {
        ArrayList<Cell> allVictims = map.getAllByType(victim);
        if (!allVictims.isEmpty()) {
            int min = heuristic(start, allVictims.get(0));
            Cell res = allVictims.get(0);
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
