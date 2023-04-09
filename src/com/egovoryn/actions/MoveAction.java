package com.egovoryn.actions;

import com.egovoryn.Cell;
import com.egovoryn.WorldMap;
import com.egovoryn.dynamics.Creature;

import java.util.Map;

public class MoveAction extends Action {
    @Override
    public void perform(WorldMap map) {
        for (Map.Entry<Cell, Creature> entry : map.getEntitiesOfType(Creature.class).entrySet()) {
            Creature creature = entry.getValue();
            creature.makeMove(entry.getKey(), map);
        }
    }
}
