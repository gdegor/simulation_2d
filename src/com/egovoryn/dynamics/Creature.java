package com.egovoryn.dynamics;

import com.egovoryn.*;
import com.egovoryn.statics.Grass;

public abstract class Creature extends Entity {
    private final int speedMove;
    private final Class<? extends Entity> victim;
    protected int healthPoints;
    protected int bellyful;


    public Creature() {
        victim = this.getClass() == Predator.class ? Herbivore.class : Grass.class;
        speedMove = this.getClass() == Predator.class ? 2 : 1;
        healthPoints = this.getClass() == Predator.class ? 3 : 5;
        bellyful = this.getClass() == Predator.class ? 5 : 4;
    }

    public void makeMove(Cell start, WorldMap map) {
        if (bellyful <= 0) healthPoints--;
        if (healthPoints <= 0) {
            map.clearCell(start);
            return;
        }
        PathFinderAStar pathFinder = new PathFinderAStar(victim);
        Cell goal = pathFinder.smellVictim(map, start);
        if (goal != null) {
            ListForPathfinder<Cell> path = pathFinder.getPathToGoal(start, goal, map);
            if (path != null && !path.isEmpty()) {
                int maxStepsPerMove = Math.min(speedMove, path.size());
                Cell move = path.get(maxStepsPerMove);
                if (move.equals(goal)) move = path.get(maxStepsPerMove - 1);
                map.makeMove(start, move);
            }
        }
    }
}
