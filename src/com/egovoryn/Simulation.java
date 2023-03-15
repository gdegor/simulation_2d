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
        System.out.println("=======================================");
        System.out.println("Welcome to the Simulation!");

        WorldMap map = new WorldMap();
        RenderPicture renderer = new RenderPicture();

        initWorld(map);
        renderer.drawMap(map);
        map.numberIteration = 1;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("You can enter:");
            System.out.println("1 - Make one step of simulation");
            System.out.println("2 - Start endless simulation");
            System.out.println("3 - Generate a new map");
            System.out.println("0 - Exit");

            switch (scanner.next()) {
                case "1" -> nextTurn(map, renderer);
                case "2" -> startSimulation(map, renderer);
                case "3" -> {
                    System.out.println("\033[H\033[2J");
                    map.numberIteration = 1;
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
        while (!map.getAllByType(TypeEntity.HERBIVORE).isEmpty()) {
            if (checkUserEnter == 2) {
                nextTurn(map, renderer);
                System.out.println("You can enter: 1 - to pause, 2 - to continue, 3 - to stop");
            }
            checkUserEnter = pauseSimulation(checkUserEnter);
            if (checkUserEnter == 3) return;
        }
        System.out.println("Herbivores died.");
    }

    private static int pauseSimulation(int current) {
        Scanner scanner = new Scanner(System.in);
        try {
            Thread.sleep(2000);
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
}