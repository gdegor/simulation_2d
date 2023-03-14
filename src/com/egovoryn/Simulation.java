package com.egovoryn;

import com.egovoryn.actions.*;

import java.util.ArrayList;
import java.util.List;

// Главный класс приложения, включает в себя:
//        Карту
//        Счётчик ходов
//        Рендерер поля
//        Action - список действий, исполняемых перед стартом симуляции или на каждом ходу (детали ниже)

public class Simulation {
    public static void main(String[] args) {
        WorldMap map = new WorldMap();
        RenderPicture renderer = new RenderPicture();

        for (Action action : getInitActions(map)) {
            action.perform(map);
        }
        renderer.drawMap(map);
        pause();
        startSimulation(map, renderer);
    }

    private static void startSimulation(WorldMap map, RenderPicture renderer) {
        int i = 1;
        while (!map.getAllByType(TypeEntity.HERBIVORE).isEmpty()) {
            System.out.println("--------------------------------");
            System.out.println("Number of iteration: " + i);

            MoveAction moveAction = new MoveAction();
            moveAction.perform(map);

            renderer.drawMap(map);
            pause();

            System.out.println("\033[H\033[2J");
            i++;
        }
        System.out.println("Herbivores died.");
    }

    private static void pauseSimulation(WorldMap map) {
        // pause sim
    }

    static void pause() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            System.out.println("error in thread");
        }
    }

    private static void nextTurn(WorldMap map, RenderPicture renderer) {
        // one step
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