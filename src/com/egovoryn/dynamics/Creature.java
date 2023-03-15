package com.egovoryn.dynamics;

import com.egovoryn.Cell;
import com.egovoryn.Entity;
import com.egovoryn.TypeEntity;
import com.egovoryn.WorldMap;

import java.util.*;

public abstract class Creature extends Entity {
    private final int speedMove;
    private final TypeEntity victim;
    private int healthPoints;

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public TypeEntity getVictim() {
        return victim;
    }

    public Creature(TypeEntity type, TypeEntity victim, int speedMove, int healthPoints) {
        super(type);
        this.victim = victim;
        this.speedMove = speedMove;
        this.healthPoints = healthPoints;
    }

    protected Stack<Cell> getPathToGoal(Cell start, Cell goal, WorldMap map) {
        Queue<Cell> openCells = new PriorityQueue<>();
        openCells.add(start);
        Map<Cell, Integer> costFromStart = new HashMap<>();
        costFromStart.put(start, 0);
        Map<Cell, Cell> rootCell = new HashMap<>();
        rootCell.put(start, null);

        while (!openCells.isEmpty()) {
            Cell currentCell = openCells.poll();

            if (currentCell == goal) break;

            for (Cell nextCell : findNeighbors(currentCell)) {
                int newCost = costFromStart.get(currentCell) + costPath(nextCell, currentCell);
                if ((map.isEmptyCell(nextCell) || map.getTypeCell(nextCell) == this.getVictim())
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

        Stack<Cell> result = new Stack<>();
        Cell curr = goal;
        while (curr != start) {
            curr = rootCell.get(curr);
            if (curr == null) return null;
            result.push(curr);
        }

        return result;
    }

    static List<Cell> findNeighbors(Cell cell) {
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
        return ((Math.abs(goal.getX() - current.getX())) + Math.abs(goal.getY() - current.getY())) * 10;
    }

    private static int costPath(Cell next, Cell current) {
        return next.getY() != current.getY() && next.getX() != current.getX() ? 14 : 10;
    }

    private static boolean checkBorder(Cell cell, WorldMap map) {
        return cell.getX() < map.getX() && cell.getY() < map.getY();
    }

    public void makeMove(Cell start, WorldMap map) {
//        System.out.println(map.getEntityFromCell(start)+" "+start);
        if (getHealthPoints() <= 0) {
            map.clearCell(start);
        } else {
            Cell goal = smellVictim(map, start, getVictim());
            if (goal != null) {
                Stack<Cell> path = getPathToGoal(start, goal, map);
                if (path != null && !path.empty()) {
                    map.clearCell(start);
                    Cell move = path.pop();
//                    System.out.println("st "+start);
//                    System.out.println("mv "+move);
                    int maxSteps = Math.min(speedMove, path.size());
                    for (int i = 0; i < maxSteps; i++) {
                        move = path.pop();
                    }
//                    System.out.println("mv2 "+move);
                    map.setEntityInCell(move, this);
                }
            }
        }
    }

    static Cell smellVictim(WorldMap map, Cell start, TypeEntity victim) {
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
