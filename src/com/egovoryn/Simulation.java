package com.egovoryn;

import com.egovoryn.actions.*;
import com.egovoryn.dynamics.Herbivore;
import com.egovoryn.dynamics.Predator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Simulation {
    private final WorldMap map;

    public Simulation(WorldMap map) {
        this.map = map;
    }

    public void startSimulation(RenderPicture renderer) {
        int checkUserEnter = 2;   // 1 pause, 2 continue, 3 stop
        while (true) {
            if (finalGame()) return;
            if (checkUserEnter == 2) {
                nextTurn(renderer);
                System.out.println("You can enter: 1 - to pause, 2 - to continue, 3 - to stop");
            }
            checkUserEnter = pauseSimulation(checkUserEnter);
            if (checkUserEnter == 3) return;
        }
    }

    public void nextTurn(RenderPicture renderer) {
        if (herbivoresExist() || predatorsExist()) {
            MoveAction moveAction = new MoveAction();
            moveAction.perform(map);
            map.numberIteration++;
            renderer.drawMap(map);
        } else {
            System.out.println("This world is died. Generate a new map.\n");
        }
    }

    public void initWorld() {
        map.clearMap();
        for (Action action : getInitActions()) {
            action.perform(map);
        }
    }

    private List<Action> getInitActions() {
        List<Action> initActions = new ArrayList<>();
        initActions.add(new GrassSpawnAction(map));
        initActions.add(new HerbivoreSpawnAction(map));
        initActions.add(new PredatorSpawnAction(map));
        initActions.add(new RockSpawnAction(map));
        initActions.add(new TreeSpawnAction(map));
        return initActions;
    }

    private int pauseSimulation(int current) {
        Scanner scanner = new Scanner(System.in);
        try {
            Thread.sleep(1000);
            if (System.in.available() > 0) {
                return scanner.nextInt();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return current;
    }

    private boolean herbivoresExist() {
        return !map.getEntitiesOfType(Herbivore.class).isEmpty();
    }

    private boolean predatorsExist() {
        return !map.getEntitiesOfType(Predator.class).isEmpty();
    }

    private boolean finalGame() {
        if (!herbivoresExist() && !predatorsExist()) {
            System.out.println("This world is died. Generate a new map.\n");
            return true;
        } else if (!herbivoresExist()) {
            System.out.println("Herbivores died. Generate a new map.\n");
            return true;
        } else if (!predatorsExist()) {
            System.out.println("Predators are died. Generate a new map.\n");
            return true;
        }
        return false;
    }
}