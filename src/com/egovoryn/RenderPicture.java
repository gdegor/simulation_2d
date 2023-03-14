package com.egovoryn;

public class RenderPicture {
    // emoji list https://unicode.org/emoji/charts/full-emoji-list.html
    // emoji go to java code https://www.fileformat.info/info/unicode/char/search.htm
    static String predator = "\uD83E\uDD81";
    static String herbivore = "\uD83D\uDC37";
    static String tree = "\uD83C\uDF33";
    static String rock = "\uD83E\uDEA8";
    static String grass = "\uD83C\uDF3D";

    protected void drawMap(WorldMap map) {
        for (int i = 0; i < map.getY(); i++) {
            for (int j = 0; j < map.getX(); j++) {
                switch (map.getTypeCell(new Cell(i, j))) {
                    case PREDATOR -> System.out.print(predator + "\t");
                    case HERBIVORE -> System.out.print(herbivore + "\t");
                    case GRASS -> System.out.print(grass + "\t");
                    case TREE -> System.out.print(tree + "\t");
                    case ROCK -> System.out.print(rock + "\t");
                    default -> System.out.print("." + "\t");
                }
            }
            System.out.print("\n");
        }
    }
}
