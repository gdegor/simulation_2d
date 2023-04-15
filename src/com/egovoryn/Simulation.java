package com.egovoryn;

import com.egovoryn.actions.*;
import com.egovoryn.dynamics.Herbivore;
import com.egovoryn.dynamics.Predator;

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

    protected void startSimulation(RenderPicture renderer) {
        nextTurn(renderer);
    }

    protected boolean nextTurn(RenderPicture renderer) {
        if (herbivoresExist() || predatorsExist()) {
            MoveAction moveAction = new MoveAction();
            moveAction.perform(map);
            numberIteration++;
            renderer.drawMap(this);
            return true;
        } else {
            return false;
        }
    }

    protected void initWorld() {
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

    protected boolean herbivoresExist() {
        return !map.getEntitiesOfType(Herbivore.class).isEmpty();
    }

    protected boolean predatorsExist() {
        return !map.getEntitiesOfType(Predator.class).isEmpty();
    }
}