package main.dynamics;

import main.Cell;
import main.Entity;
import main.TypeEntity;
import main.WorldMap;
import main.statics.Grass;

import java.util.*;

public abstract class Creature extends Entity {
    private int speedMove = 2;
    private int healthPoints = 2;
    private TypeEntity victim = null;

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
                        && (map.isEmptyCell(nextCell) || map.getTypeCell(nextCell) == this.getVictim())) {
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
        return ((Math.abs(goal.getX() - current.getX())) + Math.abs(goal.getY() - current.getY())) * 10;
    }

    private static int costPath(Cell next, Cell current) {
        return next.getY() != current.getY() && next.getX() != current.getX() ? 14 : 10;
    }

    public void makeMove(Cell start, WorldMap map) {
        System.out.println(getHealthPoints());
        if (this.getType() == TypeEntity.HERBIVORE) {
            for (Cell neighbor : findNeighbors(start)) {
                if (map.getTypeCell(neighbor) == TypeEntity.PREDATOR) {
                    Predator predator = (Predator) map.getEntityFromCell(neighbor);
                    setHealthPoints(getHealthPoints() - predator.getDamageDealt());
                }
                if (map.getTypeCell(neighbor) == TypeEntity.GRASS) {
                    Grass grass = (Grass) map.getEntityFromCell(neighbor);
                    setHealthPoints(getHealthPoints() + grass.getHealPower());
                    map.clearCell(neighbor);
                }
            }
        }
        if (getHealthPoints() <= 0) {
            map.clearCell(start);
        } else {
            Cell goal = smellVictim(map, start, getVictim());
            if (goal != null) {
                Stack<Cell> path = getPathToGoal(start, goal, map);
                map.clearCell(start);
                Cell move = path.pop();
                for (int i = 0; i < speedMove; i++) {
                    if (!path.empty()) move = path.pop();
                }
                map.setEntityInCell(move, this);
            }
        }
    }

    static Cell smellVictim(WorldMap map, Cell start, TypeEntity victim) {
        ArrayList<Cell> allVictims = map.getByType(victim);
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

    static void attackVictim(Cell victim) {

    }

}
