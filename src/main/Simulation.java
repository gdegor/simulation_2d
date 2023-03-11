package main;

import main.dynamics.Predator;

import java.util.*;

public class Simulation {
    public static void main(String[] args) {
        WorldMap map = new WorldMap();
        map.initStartMap();
        RenderPicture.drawMap(map);

        Set<Cell> closedCells = new HashSet<>();
        Queue<Cell> openCells = new PriorityQueue<>();

        Stack<Cell> goodWay = new Stack<>();

        // f(x) = g(x) + h(x)
        // f(x) – вес ячейки
        // g(x) – длина пути из начальной точки в текущую ячейку (по диагонале – 14, прямо – 10)
        // h(x) – эвристическое приближение (примерная длина от текущей до финальной, игнор препятствий, умнож. на 10)

        int i = 1;
        int j = 1;

        int final_i = 4;
        int final_j = 4;

        openCells.add(new Cell(i,j));

        while (!openCells.isEmpty()) {
            Cell currentCell = openCells.poll();
            if(!goodWay.contains(currentCell)) goodWay.add(currentCell);
            i = currentCell.getY();
            j = currentCell.getX();

            if (closedCells.contains(currentCell)) {
                continue;
            }

            if (i == final_i && j == final_j) {
                System.out.println("ALL PIGS MUST DIE!!!");
                map.setEntityInCell(currentCell, new Predator());
                break;
            }

            for (int row = i - 1; row < i + 2; row++) {
                for (int col = j - 1; col < j + 2; col++) {
                    if (map.isEmptyCell(new Cell(row, col)) && (row != i || col != j)
                    && (row >= 0 && row <= final_i) && (col >= 0 && col <= final_j)) {
                        Cell tmp = new Cell(row, col);
                        int pathCost = tmp.getPathCost();
                        if (row != i && col != j) {
                            pathCost += 14;
                        } else {
                            pathCost += 10;
                        }
                        pathCost += ((final_i - row) + (final_j - col))*10;
                        tmp.setPathCost(pathCost);
                        openCells.add(tmp);
                    }
                }
            }
            closedCells.add(currentCell);
            map.setEntityInCell(currentCell, new Predator());
//            System.out.println(openCells);
        }
        System.out.println(goodWay);
        RenderPicture.drawMap(map);
    }
}





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