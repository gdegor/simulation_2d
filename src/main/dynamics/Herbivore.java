package main.dynamics;

import main.Cell;
import main.TypeEntity;
import main.WorldMap;

public class Herbivore extends Creature {
    public Cell makeMove(Cell start, WorldMap map) {
        Cell goal = smellsVictim(map);
//        System.out.println(goal);
        return super.makeMove(start, goal, map, new Herbivore());
    }

    private static Cell smellsVictim(WorldMap map) {
        for (int i = 0; i < map.getY(); i++) {
            for (int j = 0; j < map.getX(); j++) {
                if (map.getTypeCell(new Cell(i,j)) == TypeEntity.GRASS) {
                    return new Cell(i,j);
                }
            }
        }

        return new Cell(9,9);
    }

    public Herbivore() {
        super(TypeEntity.HERBIVORE, TypeEntity.GRASS, 1,5);
    }
}
