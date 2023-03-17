package com.egovoryn;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        WorldMap map = new WorldMap();
        RenderPicture renderer = new RenderPicture();
        Simulation simulation = new Simulation(map);

        simulation.initWorld();
        renderer.drawMap(map);
        map.numberIteration = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Simulation!");
        System.out.println("=======================================");
        while (true) {
            gameMenu();
            switch (scanner.next()) {
                case "1" -> simulation.nextTurn(renderer);
                case "2" -> simulation.startSimulation(renderer);
                case "3" -> {
                    System.out.println("\033[H\033[2J");
                    map.numberIteration = 0;
                    simulation.initWorld();
                    renderer.drawMap(map);
                }
                case "0" -> System.exit(0);
            }
        }
    }

    private static void gameMenu() {
        System.out.println("You can enter:");
        System.out.println("1 - Make one step of simulation");
        System.out.println("2 - Start endless simulation");
        System.out.println("3 - Generate a new map");
        System.out.println("0 - Exit");
    }
}
