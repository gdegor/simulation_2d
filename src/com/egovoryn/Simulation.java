package com.egovoryn;

import com.egovoryn.actions.*;
import com.egovoryn.dynamics.Herbivore;
import com.egovoryn.dynamics.Predator;
import com.egovoryn.statics.Rock;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final WorldMap map;
    public int numberIteration = 0;

    public Simulation() {
        map = new WorldMap();
    }

    public WorldMap getMap() {
        return map;
    }

    public void startSimulation(RenderPicture renderer) {
        nextTurn(renderer);
        System.out.println("You can enter: 1 - to pause, 2 - to continue, 3 - to stop");
    }

    public void nextTurn(RenderPicture renderer) {
        if (herbivoresExist() || predatorsExist()) {
            MoveAction moveAction = new MoveAction();
            moveAction.perform(map);
            numberIteration++;
            renderer.drawMap(this);
        } else {
            System.out.println("This world is died. Generate a new map.\n");
        }
    }

    public void initWorld() {
        map.setEntityInCell(new Cell(0, 0), new Herbivore());
        map.setEntityInCell(new Cell(1, 1), new Rock());
        map.setEntityInCell(new Cell(2, 3), new Rock());
        map.setEntityInCell(new Cell(3, 3), new Rock());
        map.setEntityInCell(new Cell(5, 5), new Predator());
//        for (Action action : getInitActions()) {
//            action.perform(map);
//        }
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

    private boolean herbivoresExist() {
        return !map.getEntitiesOfType(Herbivore.class).isEmpty();
    }

    private boolean predatorsExist() {
        return !map.getEntitiesOfType(Predator.class).isEmpty();
    }

    public boolean isSimulationOver() {
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