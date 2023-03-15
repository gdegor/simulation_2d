package com.egovoryn;

public class RenderPicture {
    // emoji list https://unicode.org/emoji/charts/full-emoji-list.html
    // emoji go to java code https://www.fileformat.info/info/unicode/char/search.htm
    static String predator = "\uD83E\uDD81";
    static String herbivore = "\uD83D\uDC37";
    static String tree = "\uD83C\uDF33";
    static String rock = "\uD83E\uDEA8";
    static String grass = "\uD83C\uDF3D";

    public void drawMap(WorldMap map) {
        System.out.println("\033[H\033[2J");
        System.out.println("=======================================");
        System.out.println("Number of iteration: " + map.numberIteration);

        for (int i = 0; i < map.getY(); i++) {
            for (int j = 0; j < map.getX(); j++) {
                switch (map.getTypeCell(new Cell(i, j))) {
                    case PREDATOR -> System.out.print(predator + "  ");
                    case HERBIVORE -> System.out.print(herbivore + "  ");
                    case GRASS -> System.out.print(grass + "  ");
                    case TREE -> System.out.print(tree + "  ");
                    case ROCK -> System.out.print(rock + "  ");
                    default -> System.out.print("." + "  ");
                }
            }
            System.out.print("\n");
        }
        System.out.println("=======================================");
    }
}
