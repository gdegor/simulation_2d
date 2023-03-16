package com.egovoryn;

import com.egovoryn.actions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Главный класс приложения, включает в себя:
//        Карту
//        Счётчик ходов
//        Рендерер поля
//        Action - список действий, исполняемых перед стартом симуляции или на каждом ходу

public class Simulation {
    public static void main(String[] args) {

        WorldMap map = new WorldMap();
        RenderPicture renderer = new RenderPicture();

        initWorld(map);
        renderer.drawMap(map);
        map.numberIteration = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Simulation!");
        System.out.println("=======================================");
        while (true) {
            gameMenu();
            switch (scanner.next()) {
                case "1" -> {
                    if (herbivoresExist(map))
                        nextTurn(map, renderer);
                    else
                        System.out.println("\033[H\033[2J" + "Herbivores died. Generate a new map.\n");
                }
                case "2" -> startSimulation(map, renderer);
                case "3" -> {
                    System.out.println("\033[H\033[2J");
                    map.numberIteration = 0;
                    initWorld(map);
                    renderer.drawMap(map);
                }
                case "0" -> {
                    return;
                }
            }
        }
    }

    private static void startSimulation(WorldMap map, RenderPicture renderer) {
        int checkUserEnter = 2;   // 1 pause, 2 continue, 3 stop
        while (herbivoresExist(map)) {
            if (checkUserEnter == 2) {
                nextTurn(map, renderer);
                System.out.println("You can enter: 1 - to pause, 2 - to continue, 3 - to stop");
            }
            checkUserEnter = pauseSimulation(checkUserEnter);
            if (checkUserEnter == 3) return;
        }
        System.out.println("Herbivores died. Generate a new map.\n");
    }

    private static int pauseSimulation(int current) {
        Scanner scanner = new Scanner(System.in);
        try {
            Thread.sleep(1000);
            if (System.in.available() > 0) {
                return scanner.nextInt();
            }
        } catch (Exception ex) {
            System.out.println("error in thread/scanner");
        }
        return current;
    }

    private static void nextTurn(WorldMap map, RenderPicture renderer) {
        MoveAction moveAction = new MoveAction();
        moveAction.perform(map);
        map.numberIteration++;
        renderer.drawMap(map);
    }

    private static void initWorld(WorldMap map) {
        map.clearMap();
        for (Action action : getInitActions(map)) {
            action.perform(map);
        }
    }

    private static List<Action> getInitActions(WorldMap map) {
        List<Action> initActions = new ArrayList<>();
        initActions.add(new GrassSpawnAction(map));
        initActions.add(new HerbivoreSpawnAction(map));
        initActions.add(new PredatorSpawnAction(map));
        initActions.add(new RockSpawnAction(map));
        initActions.add(new TreeSpawnAction(map));
        return initActions;
    }

    private static void gameMenu() {
        System.out.println("You can enter:");
        System.out.println("1 - Make one step of simulation");
        System.out.println("2 - Start endless simulation");
        System.out.println("3 - Generate a new map");
        System.out.println("0 - Exit");
    }

    private static boolean herbivoresExist(WorldMap map) {
        return !map.getAllByType(TypeEntity.HERBIVORE).isEmpty();
    }
}