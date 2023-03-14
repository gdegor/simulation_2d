package com.egovoryn.actions;

import com.egovoryn.Cell;
import com.egovoryn.TypeEntity;
import com.egovoryn.WorldMap;
import com.egovoryn.dynamics.Herbivore;
import com.egovoryn.dynamics.Predator;

public class MoveAction extends Action {
    @Override
    public void perform(WorldMap map) {
        for (Cell location : map.getAllByType(TypeEntity.PREDATOR)) {
            Predator pr = (Predator) map.getEntityFromCell(location);
            pr.makeMove(location, map);
        }
        for (Cell location : map.getAllByType(TypeEntity.HERBIVORE)) {
            Herbivore pr = (Herbivore) map.getEntityFromCell(location);
            pr.makeMove(location, map);
        }
    }
}
