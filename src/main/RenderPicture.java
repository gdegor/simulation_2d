package main;


import java.util.Arrays;

public class RenderPicture {
    static final int N = 20; // width
    static final int M = 20; // height
    static final int sizeMap = N*M;


    // emoji list https://unicode.org/emoji/charts/full-emoji-list.html
    // emoji go to java code https://www.fileformat.info/info/unicode/char/search.htm
    static String predator = "\uD83E\uDD81";
    static String herbivore = "\uD83D\uDC37";
    static String tree = "\uD83C\uDF33";
    static String rock = "\uD83E\uDEA8";
    static String grass = "\uD83C\uDF3D";

    public static void main(String[] args) {
        float[] chancesSpawn = {0.05f, 0.10f, 0.10f, 0.05f, 0.10f}; // predators, herbivore, tree, grass, rock

        int[] allMaxCounts = new int[5];
        int[] probabilityIntervals = new int[4];

        for (int v = 0; v < 5; v++) {
            allMaxCounts[v] = (int)(sizeMap*chancesSpawn[v]);
        }
        int temp = allMaxCounts[0];
        for (int x = 0; x < 4; x++) {
            probabilityIntervals[x] = temp + allMaxCounts[x+1]; // probabilityIntervals[1] = allMaxCounts[1]+allMaxCounts[2];
            temp = probabilityIntervals[x];
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int minIndexObjects = 0;
                int maxIndexObjects = sizeMap;

                int rand = minIndexObjects + (int)(Math.random() * ((maxIndexObjects - minIndexObjects) + 1));

                if (rand < allMaxCounts[0]) {
                    System.out.print(predator + "\t");
                } else if (rand > allMaxCounts[0] && rand < probabilityIntervals[0]) {
                    System.out.print(herbivore + "\t");
                } else if (rand > probabilityIntervals[0] && rand < probabilityIntervals[1]) {
                    System.out.print(tree + "\t");
                } else if (rand > probabilityIntervals[1] && rand < probabilityIntervals[2]) {
                    System.out.print(grass + "\t");
                } else if (rand > probabilityIntervals[2] && rand < probabilityIntervals[3]) {
                    System.out.print(rock + "\t");
                } else {
                    System.out.print("." + "\t");
                }
            }
            System.out.print("\n");
        }
        System.out.println(Arrays.toString(allMaxCounts));
        System.out.println(Arrays.toString(probabilityIntervals));
    }
}
