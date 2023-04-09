package com.egovoryn;

import java.util.Scanner;

public class Main {
    public static final String NEXT_TURN_SIM = "1";
    public static final String START_ENDLESS_SIM = "2";
    public static final String GENERATE_NEW_SIM = "3";
    public static final String EXIT_FROM_SIM = "0";

    // 1 pause, 2 continue, 3 stop
    public static final int PAUSE_ENDLESS_SIM = 1;
    public static final int CONTINUE_ENDLESS_SIM = 2;
    public static final int STOP_ENDLESS_SIM = 3;

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
            gameMenu();
            switch (scanner.next()) {
                case NEXT_TURN_SIM -> simulation.nextTurn(renderer);
                case START_ENDLESS_SIM -> {
                    int checkUserEnter = CONTINUE_ENDLESS_SIM;
                    while (true) {
                        checkUserEnter = menuInSimulation(checkUserEnter);
                        if (checkUserEnter == STOP_ENDLESS_SIM || simulation.isSimulationOver()) break;
                        if (checkUserEnter == CONTINUE_ENDLESS_SIM) simulation.startSimulation(renderer);
                    }
                }
                case GENERATE_NEW_SIM -> {
                    System.out.println("\033[H\033[2J");
                    simulation = new Simulation();
                    simulation.initWorld();
                    renderer.drawMap(simulation);
                }
                case EXIT_FROM_SIM -> System.exit(0);
                default -> System.out.println("\033[H\033[2J");
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

    private static int menuInSimulation(int current) {
        Scanner scanner = new Scanner(System.in);
        try {
            Thread.sleep(1000);
            if (System.in.available() > 0) {
                int res = scanner.nextInt();
                if (res == PAUSE_ENDLESS_SIM || res == STOP_ENDLESS_SIM || res == CONTINUE_ENDLESS_SIM) return res;
                return current;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return current;
    }
}
