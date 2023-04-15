package com.egovoryn;

import java.util.Scanner;

public class Main {
    private static final String NEXT_MENU_ITEM = "1";
    private static final String START_MENU_ITEM = "2";
    private static final String GENERATE_MENU_ITEM = "3";
    private static final String EXIT_MENU_ITEM = "0";
    private static final int PAUSE_ENDLESS_SIM = 1;
    private static final int CONTINUE_ENDLESS_SIM = 2;
    private static final int STOP_ENDLESS_SIM = 3;

    public static void main(String[] args) {
        RenderPicture renderer = new RenderPicture();
        Simulation simulation = new Simulation();

        simulation.initWorld();
        renderer.drawMap(simulation);
        simulation.numberIteration = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Simulation!");
        System.out.println("=======================================");
        while (true) {
            mainGameMenu();
            switch (scanner.next()) {
                case NEXT_MENU_ITEM -> {
                    if (!simulation.nextTurn(renderer)) System.out.println("This world is died. Generate a new map.\n");
                }
                case START_MENU_ITEM -> {
                    int userInput = CONTINUE_ENDLESS_SIM;
                    while (true) {
                        userInput = inputInSimulation(userInput);
                        if (userInput == STOP_ENDLESS_SIM || isSimulationOver(simulation)) break;
                        if (userInput == CONTINUE_ENDLESS_SIM) {
                            simulation.startSimulation(renderer);
                            System.out.println("You can enter: 1 - to pause, 2 - to continue, 3 - to stop");
                        }
                    }
                }
                case GENERATE_MENU_ITEM -> {
                    System.out.println("\033[H\033[2J");
                    simulation = new Simulation();
                    simulation.initWorld();
                    renderer.drawMap(simulation);
                }
                case EXIT_MENU_ITEM -> System.exit(0);
                default -> System.out.println("\033[H\033[2J");
            }
        }
    }

    private static void mainGameMenu() {
        System.out.println("You can enter:");
        System.out.println("1 - Make one step of simulation");
        System.out.println("2 - Start endless simulation");
        System.out.println("3 - Generate a new map");
        System.out.println("0 - Exit");
    }

    private static int inputInSimulation(int current) {
        try {
            Thread.sleep(1000);
            if (System.in.available() > 0) {
                Scanner scanner = new Scanner(System.in);
                int res = scanner.nextInt();
                if (res == PAUSE_ENDLESS_SIM || res == STOP_ENDLESS_SIM || res == CONTINUE_ENDLESS_SIM) return res;
                return current;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return current;
    }

    private static boolean isSimulationOver(Simulation sim) {
        if (!sim.herbivoresExist() && !sim.predatorsExist()) {
            System.out.println("This world is died. Generate a new map.\n");
            return true;
        } else if (!sim.herbivoresExist()) {
            System.out.println("Herbivores died. Generate a new map.\n");
            return true;
        } else if (!sim.predatorsExist()) {
            System.out.println("Predators are died. Generate a new map.\n");
            return true;
        }
        return false;
    }
}
