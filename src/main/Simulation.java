package main;

import main.dynamics.Predator;
import main.statics.Grass;
import main.statics.Tree;

import java.util.*;

public class Simulation {
    public static void main(String[] args) {
        WorldMap map = new WorldMap();
        map.initStartMap();
        RenderPicture.drawMap(map);
        System.out.println("______________________");

        Cell start = new Cell(4,1);
        Cell goal = new Cell(9,9);

        Queue<Cell> queue = new PriorityQueue<>();
        queue.add(start);
        Map<Cell, Cell> cameFrom = new HashMap<>();
        cameFrom.put(start, null);
        Map<Cell, Integer> costSoFar = new HashMap<>();
        costSoFar.put(start, 0);

        while (!queue.isEmpty()) {
            Cell currentCell = queue.poll();

            if (currentCell == goal) return;

            for (Cell nextCell : findNeighbors(currentCell)) {
                int newCost = costSoFar.get(currentCell) + costPath(nextCell, currentCell);
                if (nextCell.getX() < map.getX() && nextCell.getY() < map.getY()
                    && map.isEmptyCell(nextCell)) {
                    if (!costSoFar.containsKey(nextCell) || newCost < costSoFar.get(nextCell)) {
                        costSoFar.put(nextCell, newCost);
                        nextCell.setPathCost(newCost + heuristic(nextCell, goal));
                        queue.add(nextCell);
                        cameFrom.put(nextCell, currentCell);
                    }
                }
            }
        }

        Cell curr = goal;  //  draw path to goal // DELETE
        while (curr != start) {
            curr = cameFrom.get(curr);
            map.setEntityInCell(curr, new Predator());
        }

        RenderPicture.drawMap(map);
    }

    private static List<Cell> findNeighbors (Cell cell) {
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

    private static int heuristic (Cell current, Cell goal) {
        return ((goal.getX() - current.getX()) + (goal.getY() - current.getY()))*10;
    }

    public static int costPath(Cell next, Cell current) {
        return next.getY() != current.getY() && next.getX() != current.getX() ? 14 : 10;
    }
}

//
//
//        for (int row = i - 1; row < i + 2; row++) {
//        for (int col = j - 1; col < j + 2; col++) {
//        if (map.isEmptyCell(new Cell(row, col)) && (row != i || col != j)
//        && (row >= 0 && row <= final_i) && (col >= 0 && col <= final_j)) {
//        Cell tmp = new Cell(row, col);
//
//        System.out.println("1 "+tmp);
//        int pathCost = tmp.getPathCost();
//        if (row != i && col != j) {
//        pathCost += 14;
//        } else {
//        pathCost += 10;
//        }
//        pathCost += ((final_i - row) + (final_j - col))*10;
//
//        tmp.rootCell = currentCell;
//        tmp.setPathCost(pathCost);
//
//        openCells.add(tmp);
//
//        System.out.println("2 "+tmp);
//        }
//        }
//        }



//
//
//while (!openCells.isEmpty()) {
////            System.out.println("h"+openCells);
//        Cell p = openCells.remove();
//        Cell x = p;
//
//        if (closedCells.contains(x)) {
//        continue;
//        }
//        if (x.equals(new Cell(9, 9))) {
//        System.out.println("YES");
//        return;
//        }
//        closedCells.add(x);
//
//        int direction = 0;
//        if (map.isEmptyCell(new Cell(x.getY() + 1, x.getX() + 1))) {
//        Cell tmp =  new Cell(x.getY() + 1, x.getX() + 1);
//        tmp.setPathCost(14);
//        openCells.add(tmp);
//        }
//        else if (map.isEmptyCell(new Cell(x.getY() + 1, x.getX()))) {
//        Cell tmp =  new Cell(x.getY() + 1, x.getX());
//        tmp.setPathCost(10);
//        openCells.add(tmp);
//        }
////            System.out.println(map.isEmptyCell(new Cell(x.getY() + 1, x.getX())));
//
//        RenderPicture.drawMap(map);
//        System.out.println("____________");
//        }