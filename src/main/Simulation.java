package main;

import main.dynamics.Creature;
import main.dynamics.Herbivore;
import main.dynamics.Predator;

import java.util.Stack;

public class Simulation {
    public static void main(String[] args) {
        WorldMap map = new WorldMap();
        map.initStartMap();
        RenderPicture.drawMap(map);
        System.out.println("______________________");
        Stack<Cell> visited = new Stack<>();

        for (int iter = 0; iter < 6; iter++) {
            int i, j;
            for (i = 0; i < map.getY(); i++) {
                for (j = 0; j < map.getX(); j++) {
                    if (map.getTypeCell(new Cell(i,j)) == TypeEntity.PREDATOR && !visited.contains(new Cell(i,j))) {
                        Predator pr = new Predator();
                        visited.push(pr.makeMove(new Cell(i,j), map));
//                        RenderPicture.drawMap(map);
//                        System.out.println("______________________");
                    }
                    if (map.getTypeCell(new Cell(i,j)) == TypeEntity.HERBIVORE && !visited.contains(new Cell(i,j))) {
                        Herbivore hb = new Herbivore();
                        visited.push(hb.makeMove(new Cell(i,j), map));
//                        RenderPicture.drawMap(map);
//                        System.out.println("______________________");
                    }
                    visited.push(new Cell(i,j));
                }
            }
            RenderPicture.drawMap(map);
//            System.out.println("______________________");
            try {
                Thread.sleep(2200);
            } catch(InterruptedException ex) {}
            System.out.println("\033[H\033[2J");
            visited.clear();
        }
    }
}