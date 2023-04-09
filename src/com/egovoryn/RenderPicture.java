package com.egovoryn;

import com.egovoryn.dynamics.Herbivore;
import com.egovoryn.dynamics.Predator;
import com.egovoryn.statics.Grass;
import com.egovoryn.statics.Rock;
import com.egovoryn.statics.Tree;

public class RenderPicture {
    static String predator = "\uD83E\uDD81";
    static String herbivore = "\uD83D\uDC37";
    static String tree = "\uD83C\uDF33";
    static String rock = "\uD83E\uDEA8";
    static String grass = "\uD83C\uDF3D";
    static String danger_herb = "\uD83D\uDC17";

    public void drawMap(Simulation simulation) {
        WorldMap map = simulation.getMap();
        System.out.println("\033[H\033[2J");
        System.out.println("Number of iteration: " + simulation.numberIteration);
        System.out.println("=======================================");

        for (int i = 0; i < map.getY(); i++) {
            for (int j = 0; j < map.getX(); j++) {
                Class<? extends Entity> typeCell = map.getTypeCell(new Cell(i, j));
                if (typeCell == null) {
                    System.out.print(". " + "  ");
                } else if (typeCell.equals(Predator.class)) {
                    System.out.print(predator + "  ");
                } else if (typeCell.equals(Herbivore.class)) {
                    Herbivore herbivore1 = (Herbivore) map.getEntityFromCell(new Cell(i, j));
                    if (herbivore1.danger) {
                        System.out.print(danger_herb + "  ");
                    } else {
                        System.out.print(herbivore + "  ");
                    }
                } else if (typeCell.equals(Grass.class)) {
                    System.out.print(grass + "  ");
                } else if (typeCell.equals(Tree.class)) {
                    System.out.print(tree + "  ");
                } else if (typeCell.equals(Rock.class)) {
                    System.out.print(rock + "  ");
                }
            }
            System.out.print("\n");
        }
        System.out.println("=======================================");
    }
}
