package main.dynamics;

import main.Cell;
import main.TypeEntity;
import main.WorldMap;

public class Predator extends Creature {
    int damageDealt;

    public Cell makeMove(Cell start, WorldMap map) {
        Cell goal = smellsVictim(map);
        return super.makeMove(start, goal, map, new Predator());
    }

    private static Cell smellsVictim(WorldMap map) {
        for (int i = 0; i < map.getY(); i++) {
            for (int j = 0; j < map.getX(); j++) {
                if (map.getTypeCell(new Cell(i,j)) == TypeEntity.HERBIVORE) {
                    return new Cell(i,j);
                }
            }
        }

        return new Cell(9,9);
    }

    public Predator() {
        super(TypeEntity.PREDATOR, TypeEntity.HERBIVORE, 2,3);
    }
}
