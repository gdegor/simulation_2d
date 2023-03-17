package com.egovoryn;

import com.egovoryn.dynamics.Herbivore;

public class RenderPicture {
    static String predator = "\uD83E\uDD81";
    static String herbivore = "\uD83D\uDC37";
    static String tree = "\uD83C\uDF33";
    static String rock = "\uD83E\uDEA8";
    static String grass = "\uD83C\uDF3D";
    static String danger_herb = "\uD83D\uDC17";

    public void drawMap(WorldMap map) {
        System.out.println("\033[H\033[2J");
        System.out.println("Number of iteration: " + map.numberIteration);
        System.out.println("=======================================");

        for (int i = 0; i < map.getY(); i++) {
            for (int j = 0; j < map.getX(); j++) {
                switch (map.getTypeCell(new Cell(i, j))) {
                    case PREDATOR -> System.out.print(predator + "  ");
                    case HERBIVORE -> {
                        Herbivore herbivore1 = (Herbivore) map.getEntityFromCell(new Cell(i, j));
                        if (herbivore1.danger) {
                            System.out.print(danger_herb + "  ");
                        } else {
                            System.out.print(herbivore + "  ");
                        }
                    }
                    case GRASS -> System.out.print(grass + "  ");
                    case TREE -> System.out.print(tree + "  ");
                    case ROCK -> System.out.print(rock + "  ");
                    default -> System.out.print(". " + "  ");
                }
            }
            System.out.print("\n");
        }
        System.out.println("=======================================");
    }
}
