package com.egovoryn.dynamics;

import com.egovoryn.*;
import com.egovoryn.statics.Grass;

public abstract class Creature extends Entity {
    private final int speedMove;
    private final Class<? extends Entity> victim;
    protected int healthPoints;
    protected int satiety;


    public Creature() {
        victim = this.getClass() == Predator.class ? Herbivore.class : Grass.class;
        speedMove = this.getClass() == Predator.class ? 2 : 1;
        healthPoints = this.getClass() == Predator.class ? 3 : 5;
        satiety = this.getClass() == Predator.class ? 5 : 4;
    }

    public void makeMove(Cell start, WorldMap map) {
        if (satiety <= 0) healthPoints--;
        if (healthPoints <= 0) {
            map.clearCell(start);
            return;
        }
        PathFinderAStar pathFinder = new PathFinderAStar(victim);
        PathNode goal = pathFinder.smellVictim(map, start);
        if (goal != null) {
            ListForPathfinder<PathNode> path = pathFinder.getPathToGoal(new PathNode(start), goal, map);
            if (path != null && !path.isEmpty()) {
                int maxStepsPerMove = Math.min(speedMove, path.size());
                Cell move = path.get(maxStepsPerMove);
                if (move.equals(goal)) move = path.get(maxStepsPerMove - 1);
                map.makeMove(start, move);
            }
        }
    }
}
