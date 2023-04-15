package com.egovoryn;

import com.egovoryn.dynamics.Herbivore;
import com.egovoryn.dynamics.Predator;
import com.egovoryn.statics.Grass;
import com.egovoryn.statics.Rock;
import com.egovoryn.statics.Tree;

public class RenderPicture {
    public static final String PREDATOR = "\uD83E\uDD81";
    public static final String HERBIVORE = "\uD83D\uDC37";
    public static final String DANGER_HERBIVORE = "\uD83D\uDC17";
    public static final String TREE = "\uD83C\uDF33";
    public static final String ROCK = "\uD83E\uDEA8";
    public static final String GRASS = "\uD83C\uDF3D";

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
                    System.out.print(PREDATOR + "  ");
                } else if (typeCell.equals(Herbivore.class)) {
                    Herbivore herbivore = map.getEntityFromCell(new Cell(i, j));
                    if (herbivore.canAttack) {
                        System.out.print(DANGER_HERBIVORE + "  ");
                    } else {
                        System.out.print(HERBIVORE + "  ");
                    }
                } else if (typeCell.equals(Grass.class)) {
                    System.out.print(GRASS + "  ");
                } else if (typeCell.equals(Tree.class)) {
                    System.out.print(TREE + "  ");
                } else if (typeCell.equals(Rock.class)) {
                    System.out.print(ROCK + "  ");
                }
            }
            System.out.print("\n");
        }
        System.out.println("=======================================");
    }
}
